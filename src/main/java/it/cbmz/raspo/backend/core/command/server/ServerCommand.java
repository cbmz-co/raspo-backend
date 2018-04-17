package it.cbmz.raspo.backend.core.command.server;

import it.cbmz.raspo.backend.core.command.Command;
import it.cbmz.raspo.backend.core.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.UnicastProcessor;

public abstract class ServerCommand implements Command {
	@Autowired
	private UnicastProcessor<Message> unicastProcessor;
}
