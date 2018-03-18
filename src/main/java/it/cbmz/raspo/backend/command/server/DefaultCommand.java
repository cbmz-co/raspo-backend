package it.cbmz.raspo.backend.command.server;

import it.cbmz.raspo.backend.command.Command;
import it.cbmz.raspo.backend.message.Message;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DefaultCommand extends ServerCommand {

	@Override
	public String commandName() {
		return "default";
	}

	@Override
	public void action(Message message) {

		_log.warning("default server command " + message);
	}

	private final Logger _log =
		Logger.getLogger(DefaultCommand.class.getName());
}
