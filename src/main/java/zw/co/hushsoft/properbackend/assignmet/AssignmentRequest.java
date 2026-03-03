package zw.co.hushsoft.properbackend.assignmet;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class AssignmentRequest {
    private LocalDateTime dueDate;
    private String courseID;
    private String title;
    private String comment;
    private AssignmentStatus assignmentStatus;
    private String assignmentNumber;

}
