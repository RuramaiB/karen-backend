package zw.co.hushsoft.properbackend.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.co.hushsoft.properbackend.exam.Exam;
import zw.co.hushsoft.properbackend.exam.ExamRepository;
import zw.co.hushsoft.properbackend.user.User;
import zw.co.hushsoft.properbackend.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AnswerServices {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;

    public AnswerResponse submitAnswers(AnswerRequest answerRequest) {
        User user = userRepository.findByEmail(answerRequest.getStudentEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Exam exam = examRepository.findById(answerRequest.getExamID())
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        Answer answer = new Answer();
        answer.setExam(exam);
        answer.setAnswer(answerRequest.getAnswer());
        answer.setQuestion(answerRequest.getQuestion());
        answer.setUser(user);
        answerRepository.save(answer);
        return AnswerResponse
                .builder()
                .answer(answer)
                .msg("Exam answer submitted successfully")
                .build();
    }

    public List<Answer> getAnswersByExam(String examID){
        Exam exam = examRepository.findById(examID).orElseThrow(() -> new RuntimeException("Exam not found"));
        return answerRepository.findAllByExam(exam);
    }

    public List<Answer> findAllAnswersByExamAndStudent(String examID, String studentEmail) {
        Exam exam = examRepository.findById(examID)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        User user = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return answerRepository.findAllByUserAndExam(user, exam);
    }
    public List<Answer> getAllAnswersByUser(String studentEmail) {
        User user = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return answerRepository.findAllByUser(user);
    }

    public AnswerResponse submitAnswersByExam(QARequest qaRequest) {
        User user = userRepository.findByEmail(qaRequest.getStudentEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Exam exam = examRepository.findById(qaRequest.getExamID())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        List<Answer> savedAnswers = new ArrayList<>();

        for (SingleAnswer singleAnswer : qaRequest.getAnswers()) {
            Answer answer = new Answer();
            answer.setExam(exam);
            answer.setUser(user);
            answer.setQuestion(singleAnswer.getQuestion());
            answer.setAnswer(singleAnswer.getAnswer());
            answerRepository.save(answer);
            savedAnswers.add(answer);
        }

        return AnswerResponse
                .builder()
                .msg("Exam submitted successfully with " + savedAnswers.size() + " answers.")
                .build();
    }

    public List<Answer> getAllAnswers(){
            return answerRepository.findAll();
    }
}
