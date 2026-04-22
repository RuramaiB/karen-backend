package zw.co.hushsoft.properbackend.verification;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProctoringIncidentRepository extends MongoRepository<ProctoringIncident, String> {
    List<ProctoringIncident> findByExamId(String examId);
    List<ProctoringIncident> findByStudentEmail(String studentEmail);
    List<ProctoringIncident> findByExamIdAndStudentEmail(String examId, String studentEmail);
}
