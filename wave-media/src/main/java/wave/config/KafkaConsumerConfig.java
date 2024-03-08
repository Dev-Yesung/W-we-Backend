package wave.config;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServer;

	@Bean
	public Map<String, Object> jsonConsumerConfig() {
		return KafkaCommonJsonDeserializer.getStringObjectMap(bootstrapServer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
		@Qualifier("kafkaTemplate") KafkaTemplate<String, Object> kafkaTemplate
	) {
		ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory
			= new ConcurrentKafkaListenerContainerFactory<>();
		Map<String, Object> configurations = jsonConsumerConfig();
		kafkaListenerContainerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(configurations));
		kafkaListenerContainerFactory.setReplyTemplate(kafkaTemplate);

		return kafkaListenerContainerFactory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> synchronousKafkaListenerContainerFactory(
		@Qualifier("synchronousKafkaTemplate") KafkaTemplate<String, Object> kafkaTemplate
	) {
		ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory
			= new ConcurrentKafkaListenerContainerFactory<>();
		Map<String, Object> configurations = jsonConsumerConfig();
		configurations.remove(ISOLATION_LEVEL_CONFIG);
		kafkaListenerContainerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(configurations));
		kafkaListenerContainerFactory.setReplyTemplate(kafkaTemplate);

		return kafkaListenerContainerFactory;
	}

	@Bean
	public StringJsonMessageConverter jsonMessageConverter() {
		return new StringJsonMessageConverter();
	}
}
