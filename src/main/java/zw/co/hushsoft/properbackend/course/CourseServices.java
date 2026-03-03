package zw.co.hushsoft.properbackend.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.co.hushsoft.properbackend.exception.ResourceNotFoundException;
import zw.co.hushsoft.properbackend.user.Role;
import zw.co.hushsoft.properbackend.user.User;
import zw.co.hushsoft.properbackend.user.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CourseServices {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public CourseResponse addNewCourse(CourseRequest courseRequest) {
        User user = userRepository.findByEmail(courseRequest.getLecturerEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        while (user.getRole() == Role.LECTURER){
            Course course = new Course();
            String classCode = generateClassCode();
            course.setCourseName(courseRequest.getCourseName());
            course.setClassCode(classCode);
            course.setCourseCode(courseRequest.getCourseCode());
            course.setLecturer(user);
            courseRepository.save(course);
            return CourseResponse
                    .builder()
                    .course(course)
                    .msg("Course created successfully")
                    .build();
        }
        return CourseResponse
                .builder()
                .course(null)
                .msg("User not allowed to create course")
                .build();

    }


    // Method to generate a random 6-character class code
    public String generateClassCode() {
        Random random = new Random();
        StringBuilder classCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(LETTERS.length()); // Get a random index
            char randomChar = LETTERS.charAt(randomIndex); // Get the character at that index
            classCode.append(randomChar); // Append the character to the class code
        }

        return classCode.toString(); // Return the generated class code
    }
    public CourseResponse joinCourse(String courseCode, String studentEmail) {
        User user = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Course course = courseRepository.findAllByClassCode(courseCode)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        if (course.getStudents() == null){
            course.setStudents(Collections.singletonList(user));
        } else{
            course.getStudents().add(user);
        }

        courseRepository.save(course);
        return CourseResponse
                .builder()
                .course(course)
                .msg("Course joined successfully")
                .build();
    }
    public CourseResponse leaveCourse(String courseCode, String studentEmail) {
        User user = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Course course = courseRepository.findAllByClassCode(courseCode)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        course.getStudents().remove(user);
        courseRepository.save(course);
        return CourseResponse
                .builder()
                .course(course)
                .msg("You have left the course.")
                .build();
    }
    public List<Course> getAllCoursesByStudent(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return courseRepository.findAllByStudents(user);
    }
    public CourseResponse getCourse(String courseID) {
        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return CourseResponse
                .builder()
                .course(course)
                .msg("Course found")
                .build();
    }
    public List<Course> getAllCoursesByLecturer(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found"));
        return courseRepository.findAllByLecturer(user);
    }
}
