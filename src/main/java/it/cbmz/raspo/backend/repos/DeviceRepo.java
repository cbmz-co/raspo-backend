package it.cbmz.raspo.backend.repos;

import it.cbmz.raspo.backend.model.Device;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepo extends PagingAndSortingRepository<Device, UUID> {
    Optional<Device> findByMac(@Param(value = "mac") String mac);
}
