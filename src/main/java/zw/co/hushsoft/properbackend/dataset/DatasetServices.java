package zw.co.hushsoft.properbackend.dataset;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zw.co.hushsoft.properbackend.Images.ImageData;
import zw.co.hushsoft.properbackend.Images.ImageRepository;
import zw.co.hushsoft.properbackend.exception.ResourceNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class DatasetServices {
    private final DatasetRepository datasetRepository;
    private final ImageRepository imageRepository;
    private final String FOLDER_PATH = new File("uploads/datasets/").getAbsolutePath() + File.separator;
    // private final String FOLDER_PATH = "/var/www/uploads/images/";

    // add new dataset
    public ResponseEntity<DatasetResponse> addNewDataset(DatasetRequest datasetRequest, MultipartFile datasetFile)
            throws IOException, ExecutionException, InterruptedException {
        File directory = new File(FOLDER_PATH);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs(); // Create the directory if it doesn't exist
            if (!dirCreated) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(DatasetResponse.builder()
                                .msg("Failed to create directory for storing dataset.")
                                .build());
            }
        }

        // Construct the file path
        String filepath = FOLDER_PATH + datasetFile.getOriginalFilename();

        // Save image metadata to the database
        ImageData datasetFileData = imageRepository.save(
                ImageData.builder()
                        .name(datasetFile.getOriginalFilename())
                        .type(datasetFile.getContentType())
                        .filePath(filepath)
                        .build());

        try {
            datasetFile.transferTo(new File(filepath).getAbsoluteFile());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DatasetResponse.builder()

                            .msg("Failed to upload the dataset file.")
                            .build());
        }
        if (datasetFileData != null) {
            Dataset dataset = new Dataset();
            dataset.setDatasetID("master");
            dataset.setDatasetName(datasetFile.getOriginalFilename());
            dataset.setDatasetFile(datasetFileData);
            return ResponseEntity.ok(DatasetResponse
                    .builder()
                    .dataset(datasetRepository.save(dataset))
                    .msg("New dataset added")
                    .build());
        }

        return ResponseEntity.badRequest().body(DatasetResponse.builder()
                .msg("Error creating new dataset.")
                .build());

    }

    public ResponseEntity<DatasetResponse> updateDataset(String datasetID, MultipartFile datasetFile)
            throws IOException, ExecutionException, InterruptedException {
        Dataset dataset = datasetRepository.findById(datasetID)
                .orElseGet(() -> {
                    Dataset newDataset = new Dataset();
                    newDataset.setDatasetID("master");
                    datasetRepository.save(newDataset);
                    return newDataset;
                });

        File directory = new File(FOLDER_PATH);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs(); // Create the directory if it doesn't exist
            if (!dirCreated) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(DatasetResponse.builder()
                                .msg("Failed to create directory for storing dataset.")
                                .build());
            }
        }

        // Construct the file path
        String filepath = FOLDER_PATH + datasetFile.getOriginalFilename();

        // Save image metadata to the database
        ImageData datasetFileData = imageRepository.save(
                ImageData.builder()
                        .name(datasetFile.getOriginalFilename())
                        .type(datasetFile.getContentType())
                        .filePath(filepath)
                        .build());

        // Transfer the file to the directory
        try {
            datasetFile.transferTo(new File(filepath).getAbsoluteFile());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DatasetResponse.builder()
                            .msg("Failed to upload the dataset file.")
                            .build());
        }
        if (datasetFileData != null) {
            dataset.setDatasetFile(datasetFileData);
            dataset.setDatasetName(datasetFile.getOriginalFilename());
            return ResponseEntity.ok(DatasetResponse
                    .builder()
                    .dataset(datasetRepository.save(dataset))
                    .msg("Dataset was updated.")
                    .build());
        }

        return ResponseEntity.badRequest().body(DatasetResponse.builder()
                .msg("Error updating dataset.")
                .build());

    }

    public Dataset getDataset(String datasetID) {
        return datasetRepository.findById(datasetID)
                .orElseThrow(() -> new ResourceNotFoundException("Dataset not found with ID: " + datasetID));
    }
}
