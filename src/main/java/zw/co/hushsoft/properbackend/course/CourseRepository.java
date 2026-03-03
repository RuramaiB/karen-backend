package zw.co.hushsoft.properbackend.course;

import org.springframework.data.mongodb.repository.MongoRepository;
import zw.co.hushsoft.properbackend.user.User;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    Optional<Course> findAllByClassCode(String classCode);

    Optional<Course> findByCourseCode(String courseCode);

    List<Course> findAllByStudents(User user);

    List<Course> findAllByLecturer(User user);
}
