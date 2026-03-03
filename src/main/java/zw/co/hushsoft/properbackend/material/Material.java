package zw.co.hushsoft.properbackend.material;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import zw.co.hushsoft.properbackend.Images.ImageData;
import zw.co.hushsoft.properbackend.auditing.AuditorEntity;
import zw.co.hushsoft.properbackend.course.Course;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TypeAlias("Material")
@Document
public class Material extends AuditorEntity {
    @Id
    private String materialID;
    @DBRef
    private Course course;
    private String title;
    private String comment;
    private ImageData attachments;
    @DateTimeFormat(fallbackPatterns = "dd/mm/yyyy")
    private LocalDate postedOn;
}
