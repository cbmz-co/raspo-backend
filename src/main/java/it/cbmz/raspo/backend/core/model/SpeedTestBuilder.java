package it.cbmz.raspo.backend.core.model;

import org.bson.types.ObjectId;

import java.util.function.Consumer;

public class SpeedTestBuilder {

	public String id;
	public Device device;
	public float ping;
	public String dwSpeed;
	public String upSpeed;

	public SpeedTestBuilder with(Consumer<SpeedTestBuilder> builderFunction){
		builderFunction.accept(this);
		return this;
	}

	public SpeedTest create(){
		return new SpeedTest(
			id,
			device,
			ping,
			dwSpeed,
			upSpeed
		);
	}
}
