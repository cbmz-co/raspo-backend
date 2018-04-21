package it.cbmz.raspo.backend.webapp.handler;

import it.cbmz.raspo.backend.core.model.Device;
import it.cbmz.raspo.backend.core.model.User;
import it.cbmz.raspo.backend.core.model.UserBuilder;
import it.cbmz.raspo.backend.core.repos.DeviceReactiveRepo;
import it.cbmz.raspo.backend.core.repos.UserReactiveRepo;
import org.bson.types.ObjectId;
import org.hibernate.validator.internal.util.logging.Log_$logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class DeviceHandler {
// TODO getByUser
// TODO getByMac

	@Autowired
	public DeviceHandler(
		DeviceReactiveRepo deviceReactiveRepo,
		UserReactiveRepo userReactiveRepo
	){
		this.deviceReactiveRepo = deviceReactiveRepo;
		this.userReactiveRepo = userReactiveRepo;
		this.notFound = ServerResponse.notFound().build();
	}

	public Mono<ServerResponse> listDevices(ServerRequest request) {
		Flux<Device> devices = deviceReactiveRepo.findAll();
		return ServerResponse.ok().contentType(APPLICATION_JSON)
			.body(devices, Device.class);
	}

	public Mono<ServerResponse> getDevice(ServerRequest request) {
		String deviceId = request.pathVariable("id");
		Mono<Device> deviceMono = deviceReactiveRepo.findById(deviceId);
		return deviceMono
			.flatMap(device -> ServerResponse.ok()
				.contentType(APPLICATION_JSON).body(fromObject(device)))
			.switchIfEmpty(notFound);
	}

	public Mono<ServerResponse> createDevice(ServerRequest request) {
		Mono<Device> deviceMono = request.bodyToMono(Device.class);
		return deviceMono
			.flatMap(d -> {
				d.setCreateDate(new Date());
				return ServerResponse.ok()
					.contentType(APPLICATION_JSON)
					.body(fromObject(deviceReactiveRepo.save(d)));
			})
			.switchIfEmpty(notFound);
	}

	public Mono<ServerResponse> checkRegisterDevice(ServerRequest request) {
		String deviceId = request.pathVariable("id");
		Mono<Device> deviceMono = deviceReactiveRepo.findById(deviceId);
		return deviceMono
			.flatMap(device -> {
				if (device.getUser() == null) {
					return ServerResponse.ok()
						.contentType(APPLICATION_JSON).body(fromObject(device));
				}else{
					return ServerResponse.badRequest()
						.build();
				}
			})
			.switchIfEmpty(notFound);

	}

	public Mono<ServerResponse> registerDevice(ServerRequest request) {
		
		return request.bodyToMono(
			new ParameterizedTypeReference<Map<String, Object>>() {})
				.map(p -> Tuples.of(
					(String) p.get("id"),
					(String)p.get("username"),
					(String)p.get("email")))
				.flatMap(t -> deviceReactiveRepo.findById(t.getT1())
					.flatMap(device -> userReactiveRepo.save(
						UserBuilder.of(
							null, t.getT2(), t.getT3()
						)).flatMap(user -> {
							device.setUser(user);
							device.setModifiedDate(new Date());
							return deviceReactiveRepo.save(device)
								.flatMap(saved -> ServerResponse.ok()
									.contentType(APPLICATION_JSON)
									.body(fromObject(saved)))
								.switchIfEmpty(notFound);
						})
					)
				).doOnError(e ->
					_log.warning("registration failed, exception: "
						+ e.getMessage()));

	}

	private Mono<ServerResponse> notFound;
	private DeviceReactiveRepo deviceReactiveRepo;
	private UserReactiveRepo userReactiveRepo;

	private final Logger _log =
		Logger.getLogger(DeviceHandler.class.getName());
}
