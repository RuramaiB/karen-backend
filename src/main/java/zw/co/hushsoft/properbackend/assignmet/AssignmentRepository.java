package zw.co.hushsoft.properbackend.assignmet;

import org.springframework.data.mongodb.repository.MongoRepository;
import zw.co.hushsoft.properbackend.course.Course;
import zw.co.hushsoft.properbackend.user.User;

import java.util.List;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    List<Assignment> findAllByCourse(Course course);
    List<Assignment> findAllByCourseAndAssignmentStatusOrderByPostedOnDesc(Course course);
}
