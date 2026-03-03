package zw.co.hushsoft.properbackend.exam;

import lombok.Data;
import lombok.ToString;
import zw.co.hushsoft.properbackend.course.Course;
import zw.co.hushsoft.properbackend.question.Question;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ToString
@Data
public class ExamRequest {
    private String examTitle;
    private String examDescription;
    private LocalDate dueDate;
    private String courseID;
    private List<Question> questions;
    private LocalDateTime startTime;
    private String duration;
}
