package it.cbmz.raspo.backend.repos;

import it.cbmz.raspo.backend.model.Device;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DeviceReactiveRepo extends ReactiveMongoRepository<Device, Long> {
	Mono<Device> findByMac(@Param(value = "mac") String mac);
}
