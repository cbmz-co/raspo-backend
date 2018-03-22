package it.cbmz.raspo.backend.configuration;

import it.cbmz.raspo.backend.handler.CommandHandler;
import it.cbmz.raspo.backend.handler.DeviceHandler;
import it.cbmz.raspo.backend.handler.SpeedTestHandler;
import it.cbmz.raspo.backend.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {

	@Bean
	public RouterFunction<ServerResponse> routes() {
		return route(GET("/"), request -> ServerResponse.ok()
			.body(
				BodyInserters.fromResource(new ClassPathResource("public/index.html"))));
	}

	@Bean
	public RouterFunction<ServerResponse> apiRoutes() {

		RouterFunction<ServerResponse> commandRoutes = commandRoutes(commandHandler);
		RouterFunction<ServerResponse> userRoutes = userRoutes(userHandler);
		RouterFunction<ServerResponse> deviceRoutes = deviceRoutes(deviceHandler);
		RouterFunction<ServerResponse> speedTestRoutes = speedTestRoutes(speedTestHandler);

		return nest(path("/api"), commandRoutes)
			.andNest(path("/api"), userRoutes)
			.andNest(path("/api"), deviceRoutes)
			.andNest(path("/api"), speedTestRoutes);
	}

	public RouterFunction<ServerResponse> commandRoutes(
		CommandHandler commandHandler
	) {
		return route(GET("/broadcast/{command}"), commandHandler::broadcast);
	}

	public RouterFunction<ServerResponse> userRoutes(
		UserHandler userHandler
	) {
		return route(GET("/user/{id}"), userHandler::getUser)
			.andRoute(GET("/user"), userHandler::listUsers)
			.andRoute(POST("/user"), userHandler::createUser);
	}

	public RouterFunction<ServerResponse> deviceRoutes(
		DeviceHandler deviceHandler
	) {
		return route(GET("/device/{id}"), deviceHandler::getDevice)
			.andRoute(GET("/device"), deviceHandler::listDevices);
	}

	public RouterFunction<ServerResponse> speedTestRoutes(
		SpeedTestHandler speedTestHandler
	) {
		return route(GET("/speedTest/{id}"), speedTestHandler::getSpeedTest)
			.andRoute(GET("/speedTest"), speedTestHandler::listSpeedTests);
	}

	@Autowired private CommandHandler commandHandler;
	@Autowired private UserHandler userHandler;
	@Autowired private DeviceHandler deviceHandler;
	@Autowired private SpeedTestHandler speedTestHandler;
}
