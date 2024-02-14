package wave.config;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.springframework.kafka.support.serializer.ErrorHandlingDeserializer.*;
import static org.springframework.kafka.support.serializer.JsonDeserializer.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class KafkaCommonJsonDeserializer {

	public static Map<String, Object> getStringObjectMap(String bootstrapServer) {
		Map<String, Object> configurations = new HashMap<>();
		configurations.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configurations.put(KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		configurations.put(VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		configurations.put(KEY_DESERIALIZER_CLASS, StringDeserializer.class);
		configurations.put(VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
		configurations.put(TRUSTED_PACKAGES, "*");
		// Consumer Max Fetch Size : 50MB
		configurations.put(FETCH_MAX_BYTES_CONFIG, 100_428_800);

		return configurations;
	}

}
