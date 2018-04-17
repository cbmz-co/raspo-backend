package it.cbmz.raspo.backend.core.model;

import java.util.Date;
import java.util.function.Consumer;

public class DeviceBuilder {

	public String id;
	public User user;
	public String mac;
	public Date createDate;
	public Date modifiedDate;
	public Date lastSignal;


	public DeviceBuilder with(Consumer<DeviceBuilder> builderFunction){
		builderFunction.accept(this);
		return this;
	}
	public Device create(){
		return new Device(
			id,
			user,
			mac,
			createDate,
			modifiedDate,
			lastSignal
		);
	}
}
