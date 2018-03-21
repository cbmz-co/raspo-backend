package it.cbmz.raspo.backend.handler;

import it.cbmz.raspo.backend.model.User;
import it.cbmz.raspo.backend.repos.UserReactiveRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class UserHandler {

	@Autowired
	public UserHandler(UserReactiveRepo userReactiveRepo){
		this.userReactiveRepo = userReactiveRepo;
	}

	public Mono<ServerResponse> listUsers(ServerRequest request) {
		Flux<User> users = userReactiveRepo.findAll();
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(users, User.class);
	}

	public Mono<ServerResponse> createUser(ServerRequest request) {
		Mono<User> user = request.bodyToMono(User.class);
		return ServerResponse.ok().build(userReactiveRepo.save(user.block()).then());
	}

	public Mono<ServerResponse> getUser(ServerRequest request) {
		ObjectId userId = new ObjectId(request.pathVariable("id"));
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Mono<User> userMono = userReactiveRepo.findById(userId);
		return userMono
			.flatMap(user -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(user)))
			.switchIfEmpty(notFound);
	}

	private final UserReactiveRepo userReactiveRepo;
}
