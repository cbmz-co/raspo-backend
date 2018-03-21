package it.cbmz.raspo.backend.configuration;

import it.cbmz.raspo.backend.handler.CommandHandler;
import it.cbmz.raspo.backend.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {

	@Bean
	public RouterFunction<ServerResponse> routes(){
		return route(GET("/"), request -> ServerResponse.ok()
			.body(
				BodyInserters.fromResource(new ClassPathResource("public/index.html"))));
	}

	@Bean
	public RouterFunction<ServerResponse> commandRoutes(
		CommandHandler commandHandler
	){
		return route(GET("/broadcast/{command}"), commandHandler::broadcast);
	}

	@Bean
	public RouterFunction<ServerResponse> userRoutes(
		UserHandler userHandler
	) {
		return route(GET("/user/{id}"), userHandler::getUser)
			.andRoute(GET("/user"), userHandler::listUsers)
			.andRoute(POST("/user"), userHandler::createUser);
	}
}
