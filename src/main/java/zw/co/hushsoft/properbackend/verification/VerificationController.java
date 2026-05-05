package zw.co.hushsoft.properbackend.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow frontend access
public class VerificationController {

    private final ProctoringIncidentRepository incidentRepository;
    private final zw.co.hushsoft.properbackend.notifications.NotificationsServices notificationsServices;

    @PostMapping("/log-incident")
    public ProctoringIncident logIncident(@RequestBody ProctoringIncident incident) {
        if (incident.getTimestamp() == null) {
            incident.setTimestamp(LocalDateTime.now());
        }
        
        ProctoringIncident savedIncident = incidentRepository.save(incident);
        
        // Create a notification for the lecturer
        try {
            zw.co.hushsoft.properbackend.notifications.NotificationsRequest request = new zw.co.hushsoft.properbackend.notifications.NotificationsRequest();
            request.setTitle("Suspicious Activity Detected");
            request.setMessage("Student " + incident.getStudentEmail() + " performed: " + incident.getIncidentType() + " (" + incident.getDetail() + ")");
            request.setStudentEmail(incident.getStudentEmail());
            notificationsServices.createNotification(request);
        } catch (Exception e) {
            System.err.println("Failed to create notification for incident: " + e.getMessage());
        }
        
        return savedIncident;
    }

    @GetMapping("/incidents/{examId}")
    public List<ProctoringIncident> getIncidentsForExam(@PathVariable String examId) {
        return incidentRepository.findByExamId(examId);
    }

    @GetMapping("/incidents/{examId}/{studentEmail}")
    public List<ProctoringIncident> getIncidentsForStudent(
            @PathVariable String examId, 
            @PathVariable String studentEmail) {
        return incidentRepository.findByExamIdAndStudentEmail(examId, studentEmail);
    }
}
