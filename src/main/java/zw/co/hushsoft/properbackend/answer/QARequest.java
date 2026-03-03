package zw.co.hushsoft.properbackend.answer;

import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data
@ToString
public class QARequest {
    private String examID;
    private String studentEmail;
    private List<SingleAnswer> answers; // NEW
}
