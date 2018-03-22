package it.cbmz.raspo.backend.model;

import org.bson.types.ObjectId;

import java.util.function.Consumer;

public class DeviceBuilder {

	public ObjectId id;
	public User user;
	public String mac;

	public DeviceBuilder with(Consumer<DeviceBuilder> builderFunction){
		builderFunction.accept(this);
		return this;
	}
	public Device create(){
		return new Device(id, user, mac);
	}
}
