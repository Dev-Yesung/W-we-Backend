package wave.config;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String bootstrapServer;

	@Bean
	public Map<String, Object> jsonProducerConfigs() {
		Map<String, Object> configurations = KafkaCommonJsonSerializer.getStringObjectMap(bootstrapServer);
		configurations.put(TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());
		configurations.put(ENABLE_IDEMPOTENCE_CONFIG, "true");

		return configurations;
	}

	@Bean
	public ProducerFactory<String, Object> kafkaProducerFactory() {
		return new DefaultKafkaProducerFactory<>(jsonProducerConfigs());
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(kafkaProducerFactory());
	}

}
