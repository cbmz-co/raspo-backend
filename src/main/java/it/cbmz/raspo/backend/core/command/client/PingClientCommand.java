package it.cbmz.raspo.backend.core.command.client;

import it.cbmz.raspo.backend.core.message.ClientMessage;
import it.cbmz.raspo.backend.core.message.Message;
import it.cbmz.raspo.backend.core.repos.DeviceReactiveRepo;
import it.cbmz.raspo.backend.core.repos.UserReactiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class PingClientCommand extends ClientCommand {

	@Override
	public String commandName() {
		return "pingClient";
	}

	@Override
	public void action(Message message) {
		ClientMessage clientMessage = (ClientMessage) message;
		_log.info("client ping message: " + clientMessage);
		deviceReactiveRepo
			.findByMac(clientMessage.getMac())
			.flatMap( device -> {
				device.setLastSignal(new Date());
				return deviceReactiveRepo.save(device);
			})
			.doOnError(e -> _log.warning(String.format("Error in pingClient command, caused by: %s", e.getCause())))
			.block();

	}

	@Autowired private DeviceReactiveRepo deviceReactiveRepo;
	private final Logger _log =
		Logger.getLogger(PingClientCommand.class.getName());
}
