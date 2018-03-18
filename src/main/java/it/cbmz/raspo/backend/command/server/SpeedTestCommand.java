package it.cbmz.raspo.backend.command.server;

import it.cbmz.raspo.backend.message.Message;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SpeedTestCommand extends ServerCommand {

	@Override
	public String commandName() {
		return "speedTest";
	}

	@Override
	public void action(Message message) {
		_log.info("server command speedTest " + message);
	}

	private final Logger _log =
		Logger.getLogger(SpeedTestCommand.class.getName());
}
