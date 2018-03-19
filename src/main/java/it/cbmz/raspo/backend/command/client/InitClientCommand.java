package it.cbmz.raspo.backend.command.client;

import it.cbmz.raspo.backend.message.ClientMessage;
import it.cbmz.raspo.backend.message.Message;
import it.cbmz.raspo.backend.model.Device;
import it.cbmz.raspo.backend.repos.DeviceRepo;
import it.cbmz.raspo.backend.repos.UserRepo;
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
			userRepo
				.findById(UUID.fromString(message1.getUuid()))
				.map(user -> {
					return deviceRepo
						.findByMac(message1.getMac())
						.orElseGet(() -> {
							// TODO trasformare in metodo
							Device d = new Device();
							d.setMac(message1.getMac());
							d.setUser(user);
							return deviceRepo.save(d);
						});
				})
				.ifPresent(
					device1 -> _log.info("device aggiunto, MAC: " + device1.getMac())
				);
		}else{
			_log.warning("Tipo messaggio non client: "+message);
		}

	}

	@Autowired private UserRepo userRepo;
	@Autowired private DeviceRepo deviceRepo;

	private final Logger _log =
		Logger.getLogger(InitClientCommand.class.getName());
}
