package zw.co.hushsoft.properbackend.material;

import lombok.Data;
import lombok.ToString;
import zw.co.hushsoft.properbackend.course.Course;

@Data
@ToString
public class MaterialRequest {
    private String courseID;
    private String title;
    private String comment;
}
