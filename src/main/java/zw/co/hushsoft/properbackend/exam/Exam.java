package zw.co.hushsoft.properbackend.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import zw.co.hushsoft.properbackend.auditing.AuditorEntity;
import zw.co.hushsoft.properbackend.course.Course;
import zw.co.hushsoft.properbackend.question.Question;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@TypeAlias("Exam")
@Builder
public class Exam extends AuditorEntity {
    @Id
    private String examID;
    private String examTitle;
    private String examDescription;
    private LocalDate dueDate;
    private Course course;
    private List<Question> questions;
    private LocalDateTime startTime;
    private String duration;

}
