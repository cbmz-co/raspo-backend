package it.cbmz.raspo.backend;

import it.cbmz.raspo.backend.handler.RaspoWSHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class RaspoApplication {

  /*  @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(
        EchoHandler echoHandler) {
        return route(HttpMethod.POST("/echo"), echoHandler::echo);
    }*/

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RaspoApplication.class, args);
    }
}