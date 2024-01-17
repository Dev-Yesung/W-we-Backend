package wave.config;

import java.util.Map;

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
		return KafkaCommonJsonSerializer.getStringObjectMap(bootstrapServer);
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
