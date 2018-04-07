package it.cbmz.raspo.backend.webapp.handler;

import it.cbmz.raspo.backend.model.SpeedTest;
import it.cbmz.raspo.backend.repos.SpeedTestReactiveRepo;
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
public class SpeedTestHandler {
// TODO getByDevice

	@Autowired
	public SpeedTestHandler(
		SpeedTestReactiveRepo speedTestReactiveRepo){
		this.speedTestReactiveRepo = speedTestReactiveRepo;
	}

	public Mono<ServerResponse> listSpeedTests(ServerRequest request) {
		Flux<SpeedTest> speedTests = speedTestReactiveRepo.findAll();
		return ServerResponse.ok().contentType(APPLICATION_JSON)
			.body(speedTests, SpeedTest.class);
	}

	public Mono<ServerResponse> getSpeedTest(ServerRequest request) {
		ObjectId speedTestId =
			new ObjectId(request.pathVariable("id"));
		Mono<ServerResponse> notFound =
			ServerResponse.notFound().build();
		Mono<SpeedTest> speedTestMono =
			speedTestReactiveRepo.findById(speedTestId);
		return speedTestMono
			.flatMap(speedTest -> ServerResponse.ok()
				.contentType(APPLICATION_JSON)
				.body(fromObject(speedTest)))
			.switchIfEmpty(notFound);
	}

	private final SpeedTestReactiveRepo speedTestReactiveRepo;
}
