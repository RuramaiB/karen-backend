package zw.co.hushsoft.properbackend.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import zw.co.hushsoft.properbackend.Images.ImageData;
import zw.co.hushsoft.properbackend.auditing.AuditorEntity;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TypeAlias("Dataset")
@Document
public class Dataset {
    @Id
    private String datasetID;
    private String datasetName;
    private ImageData datasetFile;
}
