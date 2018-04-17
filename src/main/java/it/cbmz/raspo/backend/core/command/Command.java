package it.cbmz.raspo.backend.core.command;

import it.cbmz.raspo.backend.core.message.Message;

public interface Command {

	String commandName();

	void action(Message message);

}
