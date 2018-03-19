package it.cbmz.raspo.backend.handler;

import it.cbmz.raspo.backend.message.ClientMessage;
import it.cbmz.raspo.backend.message.Message;
import it.cbmz.raspo.backend.subscriber.RaspoSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.util.logging.Logger;

@Component
public class RaspoWSHandler implements WebSocketHandler {

	@Autowired
	public RaspoWSHandler(
		UnicastProcessor<Message> unicastProcessor, Flux<Message> messages) {

		_unicastProcessor = unicastProcessor;
		_messages = Flux.from(messages).map(Message::toJSON);

	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {

		session.receive()
			.map(WebSocketMessage::getPayloadAsText)
			.map(ClientMessage::toMessage)
			.subscribe(_RaspoSubscriber);

		return session.send(_messages.map(session::textMessage));

	}


	@Autowired
	private RaspoSubscriber _RaspoSubscriber;

	private final UnicastProcessor<Message> _unicastProcessor;
	private final Flux<String> _messages;
	private final Logger _log =
		Logger.getLogger(RaspoWSHandler.class.getName());
}
