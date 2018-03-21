package it.cbmz.raspo.backend.repos;

import it.cbmz.raspo.backend.model.SpeedTest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpeedTestRepo extends PagingAndSortingRepository<SpeedTest, Long> {
}
