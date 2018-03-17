package it.cbmz.raspo.backend.subscriber;

import it.cbmz.raspo.backend.message.Message;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.UnicastProcessor;

import java.util.logging.Logger;

public class RaspoSubscriber implements Subscriber<Message> {

	@Override
	public void onSubscribe(Subscription s) {
	}

	//messaggi dal client
	@Override
	public void onNext(Message message) {
		switch (message.type) {
			case "init":
				break;
			case "speedtest":
				break;
			default:
				_log.info("no command found");
		}

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

}
