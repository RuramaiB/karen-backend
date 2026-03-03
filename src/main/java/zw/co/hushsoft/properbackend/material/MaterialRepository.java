package zw.co.hushsoft.properbackend.material;

import org.springframework.data.mongodb.repository.MongoRepository;
import zw.co.hushsoft.properbackend.course.Course;

import java.util.List;

public interface MaterialRepository extends MongoRepository<Material, String> {
    List<Material> findAllByCourse(Course course);

    List<Material> findAllByCourseOrderByCreatedAtDesc(Course course);
}
