package it.cbmz.raspo.backend.repos;

import it.cbmz.raspo.backend.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReactiveRepo extends ReactiveMongoRepository<User, ObjectId> {

}
