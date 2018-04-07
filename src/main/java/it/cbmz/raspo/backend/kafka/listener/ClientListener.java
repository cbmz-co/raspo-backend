package it.cbmz.raspo.backend.kafka.listener;

import it.cbmz.raspo.backend.command.Command;
import it.cbmz.raspo.backend.command.client.ClientCommand;
import it.cbmz.raspo.backend.message.ClientMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClientListener {

	@KafkaListener(topics = "client")
	public void listen(ClientMessage message) {
		System.out.println("Received Message: " + message);
		Command command = commandMap.get(message.type);
		command.action(message);
	}

	@Autowired@Qualifier("commandMap")
	private Map<String, ClientCommand> commandMap;

}
