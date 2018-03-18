package it.cbmz.raspo.backend.command.server;

import it.cbmz.raspo.backend.command.Command;
import it.cbmz.raspo.backend.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.UnicastProcessor;

public abstract class ServerCommand implements Command {
	@Autowired
	private UnicastProcessor<Message> unicastProcessor;
}
