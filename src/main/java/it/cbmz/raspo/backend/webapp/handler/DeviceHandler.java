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

import java.util.*;
import java.util.logging.Logger;

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
		Mono<Map<String, Object>> params = request.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
		Mono<Device> deviceMono = params.flatMap(p -> {
			try {
				Device d = deviceReactiveRepo.findById((String) p.get("id")).block();

				User u = userReactiveRepo.save(new UserBuilder()
					.with($ -> {
						$.username = (String) p.get("username");
						$.email = (String) p.get("email");
					}).create()).block();
				d.setUser(u);
				d.setModifiedDate(new Date());

				return deviceReactiveRepo.save(d);
			} catch (Exception e) {
				_log.warning("registration failed, exception: "+ e.getMessage());
				return Mono.empty();
			}
		});
		return deviceMono.flatMap( device -> ServerResponse.ok()
			.contentType(APPLICATION_JSON).body(fromObject(device)))
			.switchIfEmpty(notFound);
//		return params.flatMap(p -> {
//			try {
//				return deviceReactiveRepo.findById((String) p.get("id"))
//					.map( device ->
//						userReactiveRepo.save(new UserBuilder()
//							.with($ -> {
//								$.username = (String) p.get("username");
//								$.email = (String) p.get("email");
//							}).create())
//							.map( user -> {
//								device.setUser(user);
//								device.setModifiedDate(new Date());
//								return deviceReactiveRepo.save(device)
//									.flatMap( saved -> ServerResponse.ok()
//										.contentType(APPLICATION_JSON).body(fromObject(saved)))
//									.switchIfEmpty(notFound);
//
//							})
//					).block().block();
//
//			} catch (Exception e) {
//				_log.warning("registration failed, exception: "+ e.getMessage());
//				return notFound;
//			}
//		});
	}

	private Mono<ServerResponse> notFound;
	private DeviceReactiveRepo deviceReactiveRepo;
	private UserReactiveRepo userReactiveRepo;

	private final Logger _log =
		Logger.getLogger(DeviceHandler.class.getName());
}
