package zw.co.hushsoft.properbackend.course;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CourseRequest {
    private String courseName;
    private String courseCode;
    private String lecturerEmail;
}
