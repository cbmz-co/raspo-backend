package it.cbmz.raspo.backend.repos;

import it.cbmz.raspo.backend.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends PagingAndSortingRepository<User, UUID> {
}
