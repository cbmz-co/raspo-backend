package it.cbmz.raspo.backend.core.command.server;

import it.cbmz.raspo.backend.core.message.Message;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SpeedTestServerCommand extends ServerCommand {

	@Override
	public String commandName() {
		return "speedTestServer";
	}

	@Override
	public void action(Message message) {
		_log.info("server command speedTest " + message);
	}

	private final Logger _log =
		Logger.getLogger(SpeedTestServerCommand.class.getName());
}
