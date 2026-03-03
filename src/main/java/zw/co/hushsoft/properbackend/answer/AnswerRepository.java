package zw.co.hushsoft.properbackend.answer;

import org.springframework.data.mongodb.repository.MongoRepository;
import zw.co.hushsoft.properbackend.exam.Exam;
import zw.co.hushsoft.properbackend.user.User;

import java.util.List;

public interface AnswerRepository extends MongoRepository<Answer, String> {
    List<Answer> findAllByExam(Exam exam);

    List<Answer> findAllByUserAndExam(User user, Exam exam);

    List<Answer> findAllByUser(User user);
}
