package zw.co.hushsoft.properbackend.verification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ProctoringIncident {
    @Id
    private String id;
    private String examId;
    private String studentEmail;
    private String incidentType; // GAZE_DEVIATION, TAB_SWITCH, AUDIO_VOID, etc.
    private String detail;
    private Double confidence;
    private LocalDateTime timestamp;
}
