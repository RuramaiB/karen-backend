package zw.co.hushsoft.properbackend.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@TypeAlias("Question")
public class Question {
        private String title;
        private String type;
        private List<Options> options;

        // Getters and setters
}
