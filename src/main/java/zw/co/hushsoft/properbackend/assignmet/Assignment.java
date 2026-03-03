package zw.co.hushsoft.properbackend.assignmet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import zw.co.hushsoft.properbackend.material.Material;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "assignments")
@TypeAlias("Assignment")
public class Assignment extends Material {
    private String assignmentNumber;
    private LocalDateTime dueDate;
    private AssignmentStatus assignmentStatus;
    private boolean assignmentDue = false;
}
