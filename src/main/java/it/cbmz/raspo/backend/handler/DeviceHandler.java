package it.cbmz.raspo.backend.handler;

import it.cbmz.raspo.backend.model.Device;
import it.cbmz.raspo.backend.repos.DeviceReactiveRepo;
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
public class DeviceHandler {
// TODO getByUser
// TODO getByMac

	@Autowired
	public DeviceHandler(DeviceReactiveRepo deviceReactiveRepo){
		this.deviceReactiveRepo = deviceReactiveRepo;
	}

	public Mono<ServerResponse> listDevices(ServerRequest request) {
		Flux<Device> devices = deviceReactiveRepo.findAll();
		return ServerResponse.ok().contentType(APPLICATION_JSON)
			.body(devices, Device.class);
	}

	public Mono<ServerResponse> getDevice(ServerRequest request) {
		ObjectId deviceId = new ObjectId(request.pathVariable("id"));
		Mono<ServerResponse> notFound =
			ServerResponse.notFound().build();
		Mono<Device> deviceMono = deviceReactiveRepo.findById(deviceId);
		return deviceMono
			.flatMap(device -> ServerResponse.ok()
				.contentType(APPLICATION_JSON).body(fromObject(device)))
			.switchIfEmpty(notFound);
	}

	private DeviceReactiveRepo deviceReactiveRepo;
}
