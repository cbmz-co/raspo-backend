package it.cbmz.raspo.backend.subscriber;

import it.cbmz.raspo.backend.command.Command;
import it.cbmz.raspo.backend.message.Message;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.UnicastProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class RaspoSubscriber implements Subscriber<Message> {

	@Override
	public void onSubscribe(Subscription s) {
		s.request(Long.MAX_VALUE);
	}

	//messaggi dal client
	@Override
	public void onNext(Message message) {

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

	@Autowired
	private void setCommandMap(List<Command> commands) {

		Map<String, Command> _commands = commands.stream().collect(
			Collectors.toConcurrentMap(
				Command::commandName, Function.identity())
		);

		commandMap = new ConcurrentHashMap<String, Command>(_commands) {
			private Command _defaultValue = get("default");

			@Override
			public Command get(Object key) {

				Command command = super.get(key);

				if(command == null) {
					return _defaultValue;
				}

				return command;
			}
		};
	}

	private Map<String, Command> commandMap;



}
