package it.cbmz.raspo.backend.core.command.client;

import it.cbmz.raspo.backend.core.message.ClientMessage;
import it.cbmz.raspo.backend.core.message.Message;
import it.cbmz.raspo.backend.core.model.DeviceBuilder;
import it.cbmz.raspo.backend.core.model.User;
import it.cbmz.raspo.backend.core.repos.DeviceReactiveRepo;
import it.cbmz.raspo.backend.core.repos.UserReactiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class InitClientCommand extends ClientCommand {

	@Override
	public String commandName() {
		return "initClient";
	}

	@Override
	public void action(Message message) {
		ClientMessage clientMessage = (ClientMessage) message;
		_log.info("client command init " + clientMessage);

		userReactiveRepo
			.findById(clientMessage.getUserId())
			.doOnError(e -> _log.warning(String.format("No userId in message, cause: %s", e.getCause())))
			.flatMap(user -> {
				_log.info("User: " + user);
				return deviceReactiveRepo
					.findByMac(clientMessage.getMac())
					.doOnError(e -> _log.warning(String.format("No mac address in message, cause: %s", e.getCause())))
					.flatMap( device -> {
						device.setLastSignal(new Date());
						return deviceReactiveRepo.save(device);
					});
			});

	}

	@Autowired private UserReactiveRepo userReactiveRepo;
	@Autowired private DeviceReactiveRepo deviceReactiveRepo;

	private final Logger _log =
		Logger.getLogger(InitClientCommand.class.getName());
}
