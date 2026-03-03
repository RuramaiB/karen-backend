package zw.co.hushsoft.properbackend.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import zw.co.hushsoft.properbackend.auditing.AuditorEntity;
import zw.co.hushsoft.properbackend.user.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias("Course")
@Document
public class Course {
    @Id
    private String courseID;
    private String courseName;
    private String courseCode;
    private String classCode;
    @DBRef
    private User lecturer;
    private List<User> students;

}
