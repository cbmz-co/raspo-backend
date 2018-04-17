package it.cbmz.raspo.backend.core.repos;

import it.cbmz.raspo.backend.core.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReactiveRepo extends ReactiveMongoRepository<User, String> {

}
