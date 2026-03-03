package zw.co.hushsoft.properbackend.stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import zw.co.hushsoft.properbackend.course.Course;

public interface StreamRepository extends MongoRepository<Stream, String> {
    Page<Stream> findAllByCourseOrderByStreamDateDesc(Pageable pageable, Course course);
}
