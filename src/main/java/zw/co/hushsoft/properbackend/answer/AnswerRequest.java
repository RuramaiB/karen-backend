package zw.co.hushsoft.properbackend.answer;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AnswerRequest {
    private String answer;
    private String question;
    private String examID;
    private String studentEmail;
}
