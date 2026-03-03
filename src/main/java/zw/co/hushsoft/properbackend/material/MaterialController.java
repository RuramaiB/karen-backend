package zw.co.hushsoft.properbackend.material;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zw.co.hushsoft.properbackend.dataset.DatasetRequest;
import zw.co.hushsoft.properbackend.dataset.DatasetResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/material")
public class MaterialController {
    private final MaterialServices materialServices;

    @PostMapping("/add-new-course-material")
    public ResponseEntity<MaterialResponse> addNewMaterial(@ModelAttribute MaterialRequest materialRequest, MultipartFile attachments) throws IOException, ExecutionException, InterruptedException {
        return materialServices.addNewMaterial(materialRequest, attachments);
    }
    @GetMapping("/get-all-material-by-/{courseID}")
    public List<Material> getMaterialByCourse(@PathVariable String courseID) {
        return materialServices.getAllMaterialByCourse(courseID);
    }
}
