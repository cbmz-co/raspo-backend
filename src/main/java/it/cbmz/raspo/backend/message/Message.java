package it.cbmz.raspo.backend.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Message {

	public String type;
	public String message;
	@JsonCreator
	public Message(@JsonProperty("type") String type, @JsonProperty("message") String message) {
		this.type = type;
		this.message = message;
	}

	@JsonIgnore
	public String getMessage(){
		return message;
	}

	public static Message toMessage(String json) {
		try {
			return _mapper.readValue(json, Message.class);
		} catch (IOException e) {
			throw new RuntimeException("Invalid JSON:" + json, e);
		}
	}

	public static String toJSON(Message message) {
		try {
			return _mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static Message of(String type, String message) {
		return toMessage(
			String.format(
				"{\"type\":\"%s\",\"message\":\"%s\"}", type, message));
	}

	private static final ObjectMapper _mapper = new ObjectMapper();
}
