package zw.co.hushsoft.properbackend.course;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseServices courseServices;

    @PostMapping("/add-new-course")
    public CourseResponse addNewCourse(@RequestBody CourseRequest courseRequest) {
        return courseServices.addNewCourse(courseRequest);
    }
    @PostMapping("/join-class-by-/{classCode}/{studentEmail}")
    public CourseResponse joinClassByStudent(@PathVariable String classCode, @PathVariable String studentEmail) {
     return courseServices.joinCourse(classCode, studentEmail);
    }
    @GetMapping("/get-all-courses-by-/{studentEmail}")
    public List<Course> getAllCoursesByStudent(@PathVariable String studentEmail) {
        return courseServices.getAllCoursesByStudent(studentEmail);
    }
    @DeleteMapping("/leave-class-by-/{classCode}/{studentEmail}")
    public CourseResponse leaveClassByStudent(@PathVariable String classCode, @PathVariable String studentEmail) {
        return courseServices.leaveCourse(classCode, studentEmail);
    }
    @GetMapping("/get-course-by-/{courseID}")
    public CourseResponse getCourseById(@PathVariable String courseID) {
        return courseServices.getCourse(courseID);
    }
    @GetMapping("/get-all-courses-by-lecturer-/{email}")
    public List<Course> getAllCoursesByLecturer(@PathVariable String email) {
        return courseServices.getAllCoursesByLecturer(email);
    }
}
