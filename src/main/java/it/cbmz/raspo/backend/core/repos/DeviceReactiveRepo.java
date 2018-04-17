package it.cbmz.raspo.backend.core.repos;

import it.cbmz.raspo.backend.core.model.Device;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DeviceReactiveRepo extends ReactiveMongoRepository<Device, String> {
	Mono<Device> findByMac(@Param(value = "mac") String mac);
}
