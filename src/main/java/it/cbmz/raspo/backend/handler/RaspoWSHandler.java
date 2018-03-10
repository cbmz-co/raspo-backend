package it.cbmz.raspo.backend.handler;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.reactivestreams.Publisher;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@Component
public class RaspoWSHandler implements WebSocketHandler {

	@Override
	public Mono<Void> handle(WebSocketSession session) {

		if(sessions.add(session)) {
			HandshakeInfo handshakeInfo = session.getHandshakeInfo();

			List<String> hosts =
				handshakeInfo.getHeaders().get(HttpHeaders.HOST);

			hosts = hosts != null ? hosts : Collections.emptyList();

			String ip = "";

			for (String host : hosts) {
				ip = host;
			}

			_log.info("Starting WebSocket ip [" + ip + "]");

			WebSocketMessage msg = session.textMessage("ok connesso bravo");

			session.receive()
				.map(WebSocketMessage::getPayloadAsText)
				.subscribe(this::onConsumer, this::onError, this::onCompleted);

			return doSend(session, Flux.just(msg));
		}
		return Mono.empty();
	}

	private Mono<Void> doSend(WebSocketSession session, Publisher<WebSocketMessage> output) {
		return session.send(output);
	}

	private void onConsumer(String message) {
		System.out.println("consumer " + message);
	}

	private void onError(Throwable error) {
		error.printStackTrace();
	}

	private void onCompleted() {
		System.out.println("completed ");
	}

	private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();


	private static final Logger _log =
		Logger.getLogger(RaspoWSHandler.class.getName());
}
