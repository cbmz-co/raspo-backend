package it.cbmz.raspo.backend.command.client;

import it.cbmz.raspo.backend.message.Message;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DefaultClientCommand extends ClientCommand {

	@Override
	public String commandName() {
		return "defaultClient";
	}

	@Override
	public void action(Message message) {

		_log.warning("default client command " + message);
	}

	private final Logger _log =
		Logger.getLogger(DefaultClientCommand.class.getName());
}
