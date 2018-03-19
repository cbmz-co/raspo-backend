package it.cbmz.raspo.backend.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public class ClientMessage extends Message {
	private String uuid;
	private String mac;

	@JsonCreator
	public ClientMessage(
		@JsonProperty("type") String type,
		@JsonProperty("message") String message,
		@JsonProperty("uuid") String uuid,
		@JsonProperty("mac") String mac){
		super(type, message);
		this.uuid = uuid;
		this.mac = mac;
	}

	public String getUuid() {
		return uuid;
	}

	public String getMac(){
		return mac;
	}

	@Override
	public String toString() {

		return "Message{" +
			"type='" + type + '\'' +
			", message='" + message + '\'' +
			", uuid='" + uuid + '\'' +
			", mac='" + mac + '\'' +
			", properties=" + getProperties() +
			'}';
	}

	public static ClientMessage toMessage(String json) {
		try {
			return getMapper().readValue(json, ClientMessage.class);
		} catch (IOException e) {
			throw new RuntimeException("Invalid JSON:" + json, e);
		}
	}
}
