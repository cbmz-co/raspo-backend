package it.cbmz.raspo.backend.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class RaspoWSHandler implements WebSocketHandler {

	@Override
	public Mono<Void> handle(WebSocketSession session) {

		HandshakeInfo handshakeInfo = session.getHandshakeInfo();

		handshakeInfo.getHeaders().get(HttpHeaders.HOST);

		session.receive()
			.map(WebSocketMessage::getPayloadAsText)
			.subscribe(System.out::println);

		return session.send(s -> session.textMessage("prova"));
	}
}
