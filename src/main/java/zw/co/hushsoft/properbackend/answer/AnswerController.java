package zw.co.hushsoft.properbackend.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerServices answerServices;

    @PostMapping("/submit-answers")
    public AnswerResponse submitAnswers(AnswerRequest answerRequest) {
        return answerServices.submitAnswers(answerRequest);
    }
    @GetMapping("/get-answers-by-exam-/{examID}")
    public List<Answer> getAllAnswersByExam(@PathVariable String examID) {
        return answerServices.getAnswersByExam(examID);
    }
    @PostMapping("/submit-exam-answers")
    public AnswerResponse submitAnswersByExam(@RequestBody QARequest qaRequest) {
        return answerServices.submitAnswersByExam(qaRequest);
    }
    @GetMapping("/get-answers-by-exam-and-student/{examID}/{studentEmail}")
    public List<Answer> getAnswersByExamAndStudent(@PathVariable String examID, @PathVariable String studentEmail) {
        return answerServices.findAllAnswersByExamAndStudent(examID, studentEmail);
    }
    @GetMapping("/get-all-answers-by-user/{studentEmail}")
    public List<Answer> getAllAnswersByUser(@PathVariable String studentEmail) {
        return answerServices.getAllAnswersByUser(studentEmail);
    }

    @GetMapping("/get-all-answers")
    public List<Answer> getAllAnswers() {
        return answerServices.getAllAnswers();
    }
}
