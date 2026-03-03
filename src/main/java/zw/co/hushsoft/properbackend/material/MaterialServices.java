package zw.co.hushsoft.properbackend.material;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zw.co.hushsoft.properbackend.Images.ImageData;
import zw.co.hushsoft.properbackend.Images.ImageRepository;
import zw.co.hushsoft.properbackend.course.Course;
import zw.co.hushsoft.properbackend.course.CourseRepository;
import zw.co.hushsoft.properbackend.exception.ResourceNotFoundException;
import zw.co.hushsoft.properbackend.stream.StreamServices;
import zw.co.hushsoft.properbackend.stream.StreamType;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class MaterialServices {
        private final MaterialRepository materialRepository;
        private final ImageRepository imageRepository;
        private final CourseRepository courseRepository;
        private final StreamServices streamServices;
        private final String FOLDER_PATH = new File("uploads/materials/").getAbsolutePath() + File.separator;

        public ResponseEntity<MaterialResponse> addNewMaterial(MaterialRequest materialRequest,
                        MultipartFile attachments)
                        throws IOException, ExecutionException, InterruptedException {
                File directory = new File(FOLDER_PATH);
                if (!directory.exists()) {
                        boolean dirCreated = directory.mkdirs(); // Create the directory if it doesn't exist
                        if (!dirCreated) {
                                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                .body(MaterialResponse.builder()
                                                                .msg("Failed to create directory for storing materials.")
                                                                .build());
                        }
                }

                // Construct the file path
                String filepath = FOLDER_PATH + attachments.getOriginalFilename();

                // Save image metadata to the database
                ImageData attachmentsFileData = imageRepository.save(
                                ImageData.builder()
                                                .name(attachments.getOriginalFilename())
                                                .type(attachments.getContentType())
                                                .filePath(filepath)
                                                .build());

                try {
                        attachments.transferTo(new File(filepath).getAbsoluteFile());
                } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(MaterialResponse.builder()
                                                        .msg("Failed to upload the attachments.")
                                                        .build());
                }
                if (attachmentsFileData != null) {
                        Material material = new Material();
                        Course course = courseRepository.findById(materialRequest.getCourseID())
                                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                        material.setCourse(course);
                        material.setTitle(materialRequest.getTitle());
                        material.setComment(materialRequest.getComment());
                        material.setAttachments(attachmentsFileData);
                        material.setPostedOn(LocalDate.now());
                        materialRepository.save(material);
                        streamServices.addStream(materialRequest.getTitle(), materialRequest.getCourseID(),
                                        StreamType.material,
                                        material.getMaterialID());
                        return ResponseEntity.ok(MaterialResponse
                                        .builder()
                                        .material(material)
                                        .msg("Material uploaded successfully.")
                                        .build());
                }
                return ResponseEntity.ok(
                                MaterialResponse
                                                .builder()
                                                .material(null)
                                                .msg("An error occurred uploading material.")
                                                .build());
        }

        public List<Material> getAllMaterialByCourse(String courseID) {
                Course course = courseRepository.findById(courseID)
                                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                return materialRepository.findAllByCourseOrderByCreatedAtDesc(course);
        }
}
