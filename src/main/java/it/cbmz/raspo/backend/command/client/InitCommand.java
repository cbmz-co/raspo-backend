package it.cbmz.raspo.backend.command.client;

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
public class InitCommand extends ClientCommand {

	@Override
	public String commandName() {
		return "init";
	}

	@Override
	public void action(Message message) {
		_log.info("init client command " + message);
		Map<String, String> props = message.getProperties();
		userRepo
				.findById(UUID.fromString(props.get("uuid")))
				.map(user -> {
					return deviceRepo
							.findByMac(props.get("mac"))
							.orElseGet(() -> {
								// TODO trasformare in metodo
								Device d = new Device();
								d.setMac(props.get("mac"));
								d.setUser(user);
								return deviceRepo.save(d);
							});
				})
				.ifPresent(
						device1 -> _log.info("device aggiunto, MAC: "+device1.getMac())
				);

	}

	@Autowired private UserRepo userRepo;
	@Autowired private DeviceRepo deviceRepo;

	private final Logger _log =
		Logger.getLogger(InitCommand.class.getName());
}
