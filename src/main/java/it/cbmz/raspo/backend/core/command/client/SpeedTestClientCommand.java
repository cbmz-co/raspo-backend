package it.cbmz.raspo.backend.core.command.client;

import it.cbmz.raspo.backend.core.message.ClientMessage;
import it.cbmz.raspo.backend.core.message.Message;
import it.cbmz.raspo.backend.core.model.SpeedTestBuilder;
import it.cbmz.raspo.backend.core.repos.DeviceReactiveRepo;
import it.cbmz.raspo.backend.core.repos.SpeedTestReactiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static java.lang.Float.parseFloat;

@Component
public class SpeedTestClientCommand extends ClientCommand {

	@Override
	public String commandName() {
		return "speedTestClient";
	}

	@Override
	public void action(Message message) {
		ClientMessage clientMessage = (ClientMessage) message;
		_log.info("client speedTest message: " + clientMessage);
		deviceReactiveRepo.findByMac(clientMessage.getMac())
			.flatMap( device ->
				speedTestReactiveRepo
					.save(new SpeedTestBuilder()
						.with($ -> {
							$.device = device;
							$.ping = parseFloat(clientMessage.getProperties().get("ping"));
							$.dwSpeed = clientMessage.getProperties().get("dw_speed");
							$.upSpeed = clientMessage.getProperties().get("up_speed");
						})
						.create()
					)
			)
			.doOnError(e -> _log.warning(String.format("Error in speedTestClient command, caused by: %s", e.getCause())));


	}

	@Autowired private SpeedTestReactiveRepo speedTestReactiveRepo;
	@Autowired private DeviceReactiveRepo deviceReactiveRepo;
	private final Logger _log =
		Logger.getLogger(SpeedTestClientCommand.class.getName());
}
