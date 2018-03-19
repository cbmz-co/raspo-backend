package it.cbmz.raspo.backend.command.client;

import it.cbmz.raspo.backend.message.Message;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SpeedTestClientCommand extends ClientCommand {

	@Override
	public String commandName() {
		return "speedTestClient";
	}

	@Override
	public void action(Message message) {
		_log.info("client command speedTest " + message);
	}

	private final Logger _log =
		Logger.getLogger(SpeedTestClientCommand.class.getName());
}
