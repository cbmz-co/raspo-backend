package it.cbmz.raspo.backend.command;

import it.cbmz.raspo.backend.message.Message;

public interface Command {

	String commandName();

	void action(Message message);

}
