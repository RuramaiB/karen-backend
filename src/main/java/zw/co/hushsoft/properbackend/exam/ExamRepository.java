package zw.co.hushsoft.properbackend.exam;

import org.springframework.data.mongodb.repository.MongoRepository;

import zw.co.hushsoft.properbackend.course.Course;
import java.util.List;

public interface ExamRepository extends MongoRepository<Exam, String> {
    List<Exam> findAllByCourseIn(List<Course> courses);
}
