package it.cbmz.raspo.backend.websocket.configuration;

import it.cbmz.raspo.backend.webapp.handler.CommandHandler;
import it.cbmz.raspo.backend.websocket.handler.RaspoWSHandler;
import it.cbmz.raspo.backend.core.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfig {

	@Bean
	public UnicastProcessor<Message> messagePublisher(){
		return UnicastProcessor.create();
	}

	@Bean
	public Flux<Message> messages(UnicastProcessor<Message> messagePublisher) {
		return messagePublisher
			.replay(0)
			.autoConnect();
	}

	@Bean
	public HandlerMapping handlerMapping() {
		Map<String, WebSocketHandler> map = new HashMap<>();
		map.put("/websocket/raspo", _raspoWSHandler);
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setUrlMap(map);
		mapping.setOrder(-1); // before annotated controllers
		return mapping;
	}

	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter();
	}

	@Bean
	public CommandHandler commandHandler(UnicastProcessor<Message> messagePublisher){
		return new CommandHandler(messagePublisher);
	}

	@Autowired
	private RaspoWSHandler _raspoWSHandler;
}