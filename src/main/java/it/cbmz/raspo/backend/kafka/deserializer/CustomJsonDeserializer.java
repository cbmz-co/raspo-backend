package it.cbmz.raspo.backend.kafka.deserializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

public class CustomJsonDeserializer<T> extends JsonDeserializer<T>{

	private T defaultValue;

	@Override
	public T deserialize(String topic, byte[] data) {

		try {
			return super.deserialize(topic, data);
		}catch (SerializationException se){
			return defaultValue;
		}
	}

	@Override
	public T deserialize(String topic, Headers headers, byte[] data) {

		try {
			return super.deserialize(topic, headers, data);
		}catch (SerializationException se){
			return defaultValue;
		}

	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {

		super.configure(configs, isKey);
		this.defaultValue = (T) configs.get("default.value.object");
	}
}
