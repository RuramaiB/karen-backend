package zw.co.hushsoft.properbackend.dataset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatasetRepository extends MongoRepository<Dataset, String> {
}
