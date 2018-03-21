package it.cbmz.raspo.backend.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ClientMessage extends Message {
	private ObjectId userId;
	private String mac;

	@JsonCreator
	public ClientMessage(
		@JsonProperty("type") String type,
		@JsonProperty("message") String message,
		@JsonProperty("userId") ObjectId userId,
		@JsonProperty("mac") String mac){
		super(type, message);
		this.userId = userId;
		this.mac = mac;
	}

	public ObjectId getUserId() {
		return userId;
	}

	public String getMac(){
		return mac;
	}

	@Override
	public String toString() {

		return "Message{" +
			"type='" + type + '\'' +
			", message='" + message + '\'' +
			", userId='" + userId + '\'' +
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
