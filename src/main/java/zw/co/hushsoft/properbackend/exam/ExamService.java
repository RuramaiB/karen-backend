package zw.co.hushsoft.properbackend.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.co.hushsoft.properbackend.course.Course;
import zw.co.hushsoft.properbackend.course.CourseRepository;
import zw.co.hushsoft.properbackend.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import zw.co.hushsoft.properbackend.user.User;
import zw.co.hushsoft.properbackend.user.UserRepository;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public ExamResponse createExam(ExamRequest examRequest) {
        Course course = courseRepository.findById(examRequest.getCourseID())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        Exam exam = new Exam();
        exam.setExamTitle(examRequest.getExamTitle());
        exam.setExamDescription(examRequest.getExamDescription());
        exam.setDueDate(examRequest.getDueDate());
        exam.setCourse(course);
        exam.setQuestions(examRequest.getQuestions());
        exam.setStartTime(examRequest.getStartTime());
        exam.setDuration(examRequest.getDuration());
        examRepository.save(exam);
        return ExamResponse.builder().exam(exam).msg("Exam created successfully").build();
    }

    public Exam getExamByID(String examID) {
        return examRepository.findById(examID).orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public List<Exam> getAllExamsByLecturer(String lecturerEmail) {
        User lecturer = userRepository.findByEmail(lecturerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found"));
        List<Course> courses = courseRepository.findAllByLecturer(lecturer);
        return examRepository.findAllByCourseIn(courses);
    }

    public ExamResponse submitExam(ExamSubmissionRequest request) {
        try {
            // Convert duration from String to Integer
            // Exam exam = getExamByID(request.getExamID());

            // Create a new exam or update existing one
            Exam exam = Exam.builder()
                    .examID(request.getExamID())
                    .examTitle(request.getExamTitle())
                    .examDescription(request.getExamDescription())
                    .dueDate(request.getDueDate())
                    .course(request.getCourse())
                    .questions(request.getQuestions())
                    .startTime(request.getStartTime())
                    .duration(request.getDuration())
                    .build();

            // Save to the database
            Exam savedExam = examRepository.save(exam);

            // Return success response
            return ExamResponse.builder()
                    .msg("Exam submitted successfully")
                    .exam(exam)
                    .build();
        } catch (NumberFormatException e) {
            return ExamResponse.builder()
                    .msg("Invalid duration format")
                    .exam(null)
                    .build();
        } catch (Exception e) {
            return ExamResponse.builder()
                    .exam(null)
                    .msg("Failed to submit exam: " + e.getMessage())
                    .build();
        }
    }
}
