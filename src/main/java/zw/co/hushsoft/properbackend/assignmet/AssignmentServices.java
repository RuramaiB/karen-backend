package zw.co.hushsoft.properbackend.assignmet;

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
import zw.co.hushsoft.properbackend.material.Material;
import zw.co.hushsoft.properbackend.material.MaterialResponse;
import zw.co.hushsoft.properbackend.stream.StreamServices;
import zw.co.hushsoft.properbackend.stream.StreamType;
import zw.co.hushsoft.properbackend.user.User;
import zw.co.hushsoft.properbackend.user.UserRepository;
import zw.co.hushsoft.properbackend.verification.Verification;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class AssignmentServices {
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final StreamServices streamServices;
    private final String FOLDER_PATH =  "C:/Users/Saghnash/Desktop/uploaded/";
    //    private final String FOLDER_PATH =  "/var/www/uploads/images/";
    public ResponseEntity<AssignmentResponse> addNewAssignment(AssignmentRequest assignmentRequest, MultipartFile attachments) throws IOException, ExecutionException, InterruptedException {
        File directory = new File(FOLDER_PATH);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();  // Create the directory if it doesn't exist
            if (!dirCreated) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(AssignmentResponse.builder()
                                .msg("Failed to create directory for storing dataset.")
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

        // Transfer the file to the directory
        try {
            attachments.transferTo(new File(filepath));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AssignmentResponse.builder()
                            .msg("Failed to upload the assignment.")
                            .build());
        }
        if (attachmentsFileData != null) {
            Course course = courseRepository.findById(assignmentRequest.getCourseID())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            Assignment assignment = new Assignment();
            assignment.setDueDate(assignmentRequest.getDueDate());
            assignment.setCourse(course);
            assignment.setAssignmentNumber(generateAssignmentCode());
            assignment.setComment(assignmentRequest.getComment());
            assignment.setTitle(assignmentRequest.getTitle());
            System.out.println(LocalDateTime.now());
            assignment.setPostedOn(LocalDate.now());
            assignment.setAssignmentStatus(assignmentRequest.getAssignmentStatus());
            assignment.setAttachments(attachmentsFileData);
            assignmentRepository.save(assignment);
            streamServices.addStream(assignmentRequest.getTitle(), assignmentRequest.getCourseID(), StreamType.assignment, assignment.getMaterialID());
            return ResponseEntity.ok(AssignmentResponse
                    .builder()
                    .assignment(assignment)
                    .msg("Assignment uploaded successfully.")
                    .build());
        }
        return ResponseEntity.ok(
                AssignmentResponse
                        .builder()
                        .assignment(null)
                        .msg("An error occurred uploading assignment.")
                        .build());

    }

    public List<Assignment> getAllAssignmentsByCourse(String courseID) {
        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return assignmentRepository.findAllByCourse(course);
    }

    public String generateAssignmentCode() {
        int [] digits = new int[6];
        String code = "";
        for (int i = 0; i < 6; i++) {
            digits[i] = (int)(Math.random()* 10);
        }
        for (int count: digits) code += count;
        return code;
    }

    public List<Assignment> getAllActiveAssignmentsByCourse(String courseID) {
        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return assignmentRepository.findAllByCourseAndAssignmentStatusOrderByPostedOnDesc(course);
    }

    public Assignment getAssignmentByID(String assignmentID) {
        Assignment assignment = assignmentRepository.findById(assignmentID)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        if(LocalDateTime.now().isAfter(assignment.getDueDate())) {
            assignment.setAssignmentDue(true);
            assignmentRepository.save(assignment);
        }
        return assignment;
    }
}
