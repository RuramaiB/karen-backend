package zw.co.hushsoft.properbackend.exam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.hushsoft.properbackend.course.Course;
import zw.co.hushsoft.properbackend.question.Question;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamSubmissionRequest {
    private String examID;
    private String examTitle;
    private String examDescription;
    private LocalDate dueDate;
    private Course course;
    private List<Question> questions;
    private LocalDateTime startTime;
    private String duration;
}
