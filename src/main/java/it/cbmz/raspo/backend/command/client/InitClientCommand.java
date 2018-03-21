package it.cbmz.raspo.backend.command.client;

import it.cbmz.raspo.backend.message.ClientMessage;
import it.cbmz.raspo.backend.message.Message;
import it.cbmz.raspo.backend.model.Device;
import it.cbmz.raspo.backend.model.DeviceBuilder;
import it.cbmz.raspo.backend.model.User;
import it.cbmz.raspo.backend.repos.DeviceReactiveRepo;
import it.cbmz.raspo.backend.repos.DeviceRepo;
import it.cbmz.raspo.backend.repos.UserReactiveRepo;
import it.cbmz.raspo.backend.repos.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
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
			ClientMessage message1 = (ClientMessage) message;
			_log.info("init client command " + message1);
			userReactiveRepo
				.findById(message1.getUserId())
				.blockOptional()
				.map(
					user ->
					{
						_log.info("User: " + user);
						return deviceReactiveRepo
							.findByMac(message1.getMac())
							.switchIfEmpty(deviceReactiveRepo
								.save(new DeviceBuilder()
									.with($ -> {
										$.mac = message1.getMac();
										$.user = user;
									})
									.createDevice()
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
