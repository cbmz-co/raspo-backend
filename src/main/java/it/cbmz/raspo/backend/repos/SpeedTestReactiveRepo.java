package it.cbmz.raspo.backend.repos;

import it.cbmz.raspo.backend.model.SpeedTest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeedTestReactiveRepo extends ReactiveMongoRepository<SpeedTest, Long> {
}
