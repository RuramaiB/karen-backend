package zw.co.hushsoft.properbackend.assignmet;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {
    private final AssignmentServices assignmentServices;

    @PostMapping("/add-new-assignment")
    public ResponseEntity<AssignmentResponse> addNewAssignment(@ModelAttribute AssignmentRequest assignmentRequest, MultipartFile attachments) throws IOException, ExecutionException, InterruptedException {
        return assignmentServices.addNewAssignment(assignmentRequest, attachments);
    }
    @GetMapping("/get-all-assignments-by-/{courseID}")
    public List<Assignment> getAllAssignmentsByCourseID(@PathVariable String courseID){
        return assignmentServices.getAllAssignmentsByCourse(courseID);
    }
    @GetMapping("/get-all-active-assignments-by-/{courseID}")
    public List<Assignment> getAllActiveAssignmentsByCourseID(@PathVariable String courseID){
        return assignmentServices.getAllActiveAssignmentsByCourse(courseID);
    }
    @GetMapping("/date")
    public LocalDateTime getDate(){
        return LocalDateTime.now();
    }

    @GetMapping("/get-assignment-by-/{assignmentID}")
    public Assignment getAssignmentByID(@PathVariable String assignmentID){
        return assignmentServices.getAssignmentByID(assignmentID);
    }
}
