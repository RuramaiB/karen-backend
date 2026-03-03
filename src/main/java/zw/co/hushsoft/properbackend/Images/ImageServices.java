package zw.co.hushsoft.properbackend.Images;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServices {
    private final ImageRepository imageRepository;
    private final String FOLDER_PATH = new File("uploads/images/").getAbsolutePath() + File.separator;

    public String uploadImage(MultipartFile file) throws IOException {
        String filepath = FOLDER_PATH + file.getOriginalFilename();
        ImageData imageData = imageRepository.save(
                ImageData
                        .builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filepath)
                        .build());

        File directory = new File(FOLDER_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        file.transferTo(new File(filepath).getAbsoluteFile());
        if (imageData != null) {
            return "File uploaded successfully" + filepath;
        }
        return null;

    }

    public byte[] downloadImage(String filename) throws IOException {
        Optional<ImageData> imageData = imageRepository.findByName(filename);
        String filePath = imageData.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());

    }
    //
}
