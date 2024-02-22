package wave.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import wave.domain.account.domain.vo.Certification;

@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServer;

	@Bean
	public Map<String, Object> jsonConsumerConfig() {
		return KafkaCommonJsonDeserializer.getStringObjectMap(bootstrapServer);
	}

	@Bean
	public ConsumerFactory<String, Object> kafkaConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(jsonConsumerConfig());
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory
			= new ConcurrentKafkaListenerContainerFactory<>();
		kafkaListenerContainerFactory.setConsumerFactory(kafkaConsumerFactory());

		return kafkaListenerContainerFactory;
	}

	@Bean
	public StringJsonMessageConverter jsonMessageConverter() {
		return new StringJsonMessageConverter();
	}
}
