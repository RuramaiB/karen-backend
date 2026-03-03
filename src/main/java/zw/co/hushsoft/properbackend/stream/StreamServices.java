package zw.co.hushsoft.properbackend.stream;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import zw.co.hushsoft.properbackend.course.Course;
import zw.co.hushsoft.properbackend.course.CourseRepository;
import zw.co.hushsoft.properbackend.exception.ResourceNotFoundException;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class StreamServices {
    private final StreamRepository streamRepository;
    private final CourseRepository courseRepository;

    public void addStream(String streamTitle, String courseID, StreamType streamType, String contentID) {
        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        Stream stream = new Stream();
        stream.setCourse(course);
        stream.setContentID(contentID);
        stream.setStreamDate(LocalDate.now());
        stream.setStreamTitle(streamTitle);
        stream.setStreamType(streamType);
        streamRepository.save(stream);
    }

    public Page<Stream> getAllStreamsByCourse(String courseID) {
        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return streamRepository.findAllByCourseOrderByStreamDateDesc(PageRequest.of(0, 10), course);
    }
}
