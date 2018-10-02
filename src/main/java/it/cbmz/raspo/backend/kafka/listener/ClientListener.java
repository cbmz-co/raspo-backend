package it.cbmz.raspo.backend.kafka.listener;

import it.cbmz.raspo.backend.core.command.Command;
import it.cbmz.raspo.backend.core.command.client.ClientCommand;
import it.cbmz.raspo.backend.core.message.ClientMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;


public class ClientListener {

	@KafkaListener(topics = "client")
	public void listen(ClientMessage message) {
		System.out.println("Received Message: " + message);
		try {
			Command command = commandMap.get(message.type);
			command.action(message);
		} catch (Exception e) {
			_log.warning(String.format("Command not founded, caused by: %s", e.getCause()));
			e.printStackTrace();
		}
	}

	@Autowired@Qualifier("commandMap")
	private Map<String, ClientCommand> commandMap;
	private final Logger _log =
		Logger.getLogger(ClientListener.class.getName());

}
