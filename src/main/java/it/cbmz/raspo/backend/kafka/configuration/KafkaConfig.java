package it.cbmz.raspo.backend.kafka.configuration;

import it.cbmz.raspo.backend.core.message.ClientMessage;
import it.cbmz.raspo.backend.kafka.deserializer.CustomJsonDeserializer;
import it.cbmz.raspo.backend.kafka.listener.ClientListener;
import it.cbmz.raspo.backend.kafka.serde.CustomJsonSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@EnableKafkaStreams
@Configuration
public class KafkaConfig {
/**
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String>
	kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, String> factory
			= new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(1); // creo un solo consumer
		return factory;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {

		Map<String, Object> props = new HashMap<>();
		props.put(
			ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
			"localhost:9092");
		props.put(
			ConsumerConfig.GROUP_ID_CONFIG,
			"raspo");
		props.put(
			ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
			StringDeserializer.class);
		props.put(
			ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
			CustomJsonDeserializer.class);
		props.put(
			JsonDeserializer.DEFAULT_VALUE_TYPE,
			ClientMessage.class);
		props.put(
			"default.value.object",
			new ClientMessage(null, null, null, null)
		);
		props.put(
			ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
			"earliest");



		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ClientListener clientListener(){
		return new ClientListener();
	}
**/
	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public StreamsConfig kStreamsConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "raspoKStream");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CustomJsonSerde.class.getName());
		props.put(JsonDeserializer.VALUE_DEFAULT_TYPE,	ClientMessage.class);
		props.put("default.value.object", new ClientMessage(null, null, null, null));
		props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName());
		return new StreamsConfig(props);
	}

	@Bean
	public KStream<String, ClientMessage> kStream(StreamsBuilder kStreamBuilder) {
		KStream<String, ClientMessage> stream = kStreamBuilder.stream("client");
		stream
			.peek((key, value) -> System.out.println("key: "+key+", value: "+value));
		return stream;
	}
}