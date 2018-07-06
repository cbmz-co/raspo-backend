package it.cbmz.raspo.backend.websocket.subscriber;

import it.cbmz.raspo.backend.core.command.client.ClientCommand;
import it.cbmz.raspo.backend.core.message.ClientMessage;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class RaspoSubscriber implements Subscriber<ClientMessage> {

	@Override
	public void onSubscribe(Subscription s) {
		s.request(Long.MAX_VALUE);
	}

	//messaggi dal client
	@Override
	public void onNext(ClientMessage message) {

		commandMap.get(message.type).action(message);

	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
		_log.info("onComplete");
	}

	private final Logger _log =
		Logger.getLogger(RaspoSubscriber.class.getName());

	@Autowired@Qualifier("commandMap") //Inietto il bean di ApplicationConfig
	private Map<String, ClientCommand> commandMap;



}
