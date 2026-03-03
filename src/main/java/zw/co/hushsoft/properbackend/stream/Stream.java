package zw.co.hushsoft.properbackend.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import zw.co.hushsoft.properbackend.course.Course;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@TypeAlias("Stream")
public class Stream {
    @Id
    private String streamID;
    private String streamTitle;
    private LocalDate streamDate;
    private String contentID;
    private StreamType streamType;
    @DBRef
    private Course course;
}
