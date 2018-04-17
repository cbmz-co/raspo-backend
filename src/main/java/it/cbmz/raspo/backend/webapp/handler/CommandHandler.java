package it.cbmz.raspo.backend.webapp.handler;

import it.cbmz.raspo.backend.core.message.Message;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

public class CommandHandler {

	public CommandHandler(UnicastProcessor<Message> messagePublisher){
		this.messagePublisher = messagePublisher;
	}

	public Mono<ServerResponse> broadcast(ServerRequest request) {
		String command = request.pathVariable("command");
		messagePublisher.onNext(Message.of(command, ""));
		return ServerResponse.ok()
			.body(BodyInserters.fromObject("Command sent: "+request.pathVariable("command")));
	}


	private UnicastProcessor<Message> messagePublisher;
}
