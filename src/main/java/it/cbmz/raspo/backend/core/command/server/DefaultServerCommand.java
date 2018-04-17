package it.cbmz.raspo.backend.core.command.server;

import it.cbmz.raspo.backend.core.message.Message;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DefaultServerCommand extends ServerCommand {

	@Override
	public String commandName() {
		return "defaultServer";
	}

	@Override
	public void action(Message message) {

		_log.warning("default server command " + message);
	}

	private final Logger _log =
		Logger.getLogger(DefaultServerCommand.class.getName());
}
