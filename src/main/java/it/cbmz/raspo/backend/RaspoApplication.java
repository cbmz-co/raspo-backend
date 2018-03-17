package it.cbmz.raspo.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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