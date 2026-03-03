package zw.co.hushsoft.properbackend.dataset;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatasetInitializer implements CommandLineRunner {

    private final DatasetRepository datasetRepository;

    @Override
    public void run(String... args) {
        if (!datasetRepository.existsById("master")) {
            log.info("Bootstrapping master dataset record...");
            Dataset masterDataset = new Dataset();
            masterDataset.setDatasetID("master");
            masterDataset.setDatasetName("Master Dataset");
            datasetRepository.save(masterDataset);
        } else {
            log.info("Master dataset record already exists.");
        }
    }
}
