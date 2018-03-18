package it.cbmz.raspo.backend.command;

import it.cbmz.raspo.backend.message.Message;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class InitCommand implements Command {

	@Override
	public String commandName() {
		return "init";
	}

	@Override
	public void action(Message message) {
		_log.info("init command " + message);
	}

	private final Logger _log =
		Logger.getLogger(InitCommand.class.getName());
}
