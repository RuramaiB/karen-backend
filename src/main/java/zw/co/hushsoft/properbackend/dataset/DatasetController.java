package zw.co.hushsoft.properbackend.dataset;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zw.co.hushsoft.properbackend.exception.ResourceNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/dataset")
@RequiredArgsConstructor
public class DatasetController {
    private final DatasetServices datasetServices;

    @PostMapping("/add-new-dataset")
    public ResponseEntity<DatasetResponse> addNewDataset(@ModelAttribute DatasetRequest dataset, MultipartFile datasetFile) throws IOException, ExecutionException, InterruptedException {
        return datasetServices.addNewDataset(dataset, datasetFile);
    }
    @PutMapping("/update-dataset-by-/{datasetID}")
    public ResponseEntity<DatasetResponse> updateDatasetByID(@PathVariable String datasetID, MultipartFile datasetFile) throws IOException, ExecutionException, InterruptedException {
        return datasetServices.updateDataset(datasetID, datasetFile);
    }
    @GetMapping("/get-dataset-by-/{datasetID}")
    public ResponseEntity<Object> getDatasetByID(@PathVariable String datasetID) {
        Dataset dataset = datasetServices.getDataset(datasetID);

        if (dataset == null || dataset.getDatasetFile().getFilePath() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dataset not found"); // Handle not found
        }

        try {
            Path filePath = Paths.get(dataset.getDatasetFile().getFilePath());
            byte[] fileBytes = Files.readAllBytes(filePath);

            // Convert bytes to the dataset object (assuming JSON format for clarity)
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> datasetJson = objectMapper.readValue(fileBytes, new TypeReference<>() {});

            // Ensure the "label" is a string and "descriptors" is properly structured
            for (Map<String, Object> entry : datasetJson) {
                if (!(entry.get("email") instanceof String)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Invalid dataset: 'email' must be a string.");
                }
                if (!(entry.get("descriptors") instanceof List)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Invalid dataset: 'descriptors' must be an array of numbers.");
                }
            }

            // Return as JSON
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(datasetJson);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading dataset");
        }
    }


//    @GetMapping("/get-dataset-by-/{datasetID}")
//    public ResponseEntity<byte[]> getDatasetByID(@PathVariable String datasetID) {
//        Dataset dataset = datasetServices.getDataset(datasetID);
//
//        if (dataset == null || dataset.getDatasetFile().getFilePath() == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Handle not found
//        }
//
//        try {
//            Path filePath = Paths.get(dataset.getDatasetFile().getFilePath());
//            byte[] imageBytes = Files.readAllBytes(filePath);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_TYPE, dataset.getDatasetFile().getType()); // Set content type
//            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle errors
//        }
//    }
}
