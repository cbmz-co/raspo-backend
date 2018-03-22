package it.cbmz.raspo.backend.command.client;

import it.cbmz.raspo.backend.message.ClientMessage;
import it.cbmz.raspo.backend.message.Message;
import it.cbmz.raspo.backend.model.SpeedTestBuilder;
import it.cbmz.raspo.backend.repos.DeviceReactiveRepo;
import it.cbmz.raspo.backend.repos.SpeedTestReactiveRepo;
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
		if (message instanceof ClientMessage) {
			ClientMessage clientMessage = (ClientMessage) message;
			_log.info("client command speedTest " + clientMessage);
			deviceReactiveRepo.findByMac(clientMessage.getMac())
				.blockOptional()
				.map( device -> {
					return speedTestReactiveRepo
						.save(new SpeedTestBuilder()
							.with($ -> {
								$.device = device;
								$.ping = parseFloat(clientMessage.getProperties().get("ping"));
								$.dwSpeed = clientMessage.getProperties().get("dw_speed");
								$.upSpeed = clientMessage.getProperties().get("up_speed");
							})
							.create()
						).blockOptional();
				})
				.get()
				.ifPresent(speedTest ->
					_log.info("SpeedTest added, MAC: "+speedTest.getDevice().getMac()));

		}else{
			_log.warning("No valid message: "+message);
		}

	}

	@Autowired private SpeedTestReactiveRepo speedTestReactiveRepo;
	@Autowired private DeviceReactiveRepo deviceReactiveRepo;

	private final Logger _log =
		Logger.getLogger(SpeedTestClientCommand.class.getName());
}
