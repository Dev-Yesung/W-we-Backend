package wave.config;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class KafkaCommonJsonSerializer {

	public static Map<String, Object> getStringObjectMap(String bootstrapServer) {
		Map<String, Object> configurations = new HashMap<>();
		configurations.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configurations.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configurations.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return configurations;
	}
}
