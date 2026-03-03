package zw.co.hushsoft.properbackend.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleAnswer {
    private String question;
    private String answer;
}
