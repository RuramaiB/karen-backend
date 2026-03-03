package zw.co.hushsoft.properbackend.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import zw.co.hushsoft.properbackend.exam.Exam;
import zw.co.hushsoft.properbackend.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@TypeAlias("Answer")
public class Answer {
    @Id
    private String answerID;
    @DBRef
    private Exam exam;
    private String answer;
    private String question;
    @DBRef
    private User user;
}
