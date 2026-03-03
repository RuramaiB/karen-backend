package zw.co.hushsoft.properbackend.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @PostMapping("/add-new-exam")
    public ExamResponse addNewExam(@RequestBody ExamRequest examRequest) {
        return examService.createExam(examRequest);
    }

    @GetMapping("/get-exam/{examID}")
    public Exam getExam(@PathVariable String examID) {
        return examService.getExamByID(examID);
    }

    @GetMapping("/get-all-exams")
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/get-all-exams-by-lecturer-/{lecturerEmail}")
    public List<Exam> getAllExamsByLecturer(@PathVariable String lecturerEmail) {
        return examService.getAllExamsByLecturer(lecturerEmail);
    }
}
