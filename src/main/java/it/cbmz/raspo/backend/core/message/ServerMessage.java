package it.cbmz.raspo.backend.core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerMessage extends Message{

	public ServerMessage(
		@JsonProperty("type") String type,
		@JsonProperty("message") String message){
		super(type, message);
	}

}
