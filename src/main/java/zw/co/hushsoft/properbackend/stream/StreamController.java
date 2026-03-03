package zw.co.hushsoft.properbackend.stream;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stream")
public class StreamController {
    private final StreamServices streamServices;

    @GetMapping("/get-all-streams-by-/{courseID}")
    public Page<Stream> getAllStreamsByCourse(@PathVariable String courseID) {
        return streamServices.getAllStreamsByCourse(courseID);
    }
}
