package it.cbmz.raspo.backend.command.client;

import it.cbmz.raspo.backend.message.ClientMessage;
import it.cbmz.raspo.backend.message.Message;
import it.cbmz.raspo.backend.model.DeviceBuilder;
import it.cbmz.raspo.backend.repos.DeviceReactiveRepo;
import it.cbmz.raspo.backend.repos.UserReactiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class InitClientCommand extends ClientCommand {

	@Override
	public String commandName() {
		return "initClient";
	}

	@Override
	public void action(Message message) {
		if (message instanceof ClientMessage) {
			ClientMessage clientMessage = (ClientMessage) message;
			_log.info("client command init " + clientMessage);
			userReactiveRepo
				.findById(clientMessage.getUserId())
				.blockOptional()
				.map(
					user ->
					{
						_log.info("User: " + user);
						return deviceReactiveRepo
							.findByMac(clientMessage.getMac())
							.switchIfEmpty(deviceReactiveRepo
								.save(new DeviceBuilder()
									.with($ -> {
										$.mac = clientMessage.getMac();
										$.user = user;
									})
									.create()
								)
							).blockOptional();
					}
				)
				.get()
				.ifPresent(
					device1 -> _log.info("Device initialized, MAC: "+device1.getMac())
				);
		}else{
			_log.warning("No valid message: "+message);
		}

	}

	@Autowired private UserReactiveRepo userReactiveRepo;
	@Autowired private DeviceReactiveRepo deviceReactiveRepo;

	private final Logger _log =
		Logger.getLogger(InitClientCommand.class.getName());
}
