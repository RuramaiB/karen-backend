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

    @PostMapping("/log-incident")
    public ProctoringIncident logIncident(@RequestBody ProctoringIncident incident) {
        if (incident.getTimestamp() == null) {
            incident.setTimestamp(LocalDateTime.now());
        }
        return incidentRepository.save(incident);
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
