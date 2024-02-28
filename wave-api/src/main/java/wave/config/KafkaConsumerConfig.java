package wave.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServer;

	@Bean
	public Map<String, Object> jsonConsumerConfig() {
		Map<String, Object> stringObjectMap
			= KafkaCommonJsonDeserializer.getStringObjectMap(bootstrapServer);
		stringObjectMap.put(ConsumerConfig.GROUP_ID_CONFIG, "group_wave_api");

		return stringObjectMap;
	}

	@Bean
	public ConsumerFactory<String, Object> kafkaConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(jsonConsumerConfig());
	}

	@Bean
	public KafkaMessageListenerContainer<String, Object> replyMusicFileContainer(
		ConsumerFactory<String, Object> cf
	) {
		ContainerProperties containerProperties = new ContainerProperties("load_music_replies");

		return new KafkaMessageListenerContainer<>(cf, containerProperties);
	}

	@Qualifier("replyingMusicFileKafkaTemplate")
	@Bean
	public ReplyingKafkaTemplate<String, Object, Object> replyingMusicFileKafkaTemplate(
		ProducerFactory<String, Object> pf,
		@Qualifier("replyMusicFileContainer") KafkaMessageListenerContainer<String, Object> container
	) {
		return new ReplyingKafkaTemplate<>(pf, container);
	}

	@Bean
	public KafkaMessageListenerContainer<String, Object> replyImageFileContainer(
		ConsumerFactory<String, Object> cf
	) {
		ContainerProperties containerProperties = new ContainerProperties("load_Image_replies");

		return new KafkaMessageListenerContainer<>(cf, containerProperties);
	}

	@Qualifier("replyingImageFileKafkaTemplate")
	@Bean
	public ReplyingKafkaTemplate<String, Object, Object> replyingImageFileKafkaTemplate(
		ProducerFactory<String, Object> pf,
		@Qualifier("replyImageFileContainer") KafkaMessageListenerContainer<String, Object> container
	) {
		return new ReplyingKafkaTemplate<>(pf, container);
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory(
		KafkaTemplate<String, Object> kafkaTemplate
	) {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory
			= new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(kafkaConsumerFactory());
		factory.setReplyTemplate(kafkaTemplate);

		return factory;
	}

	@Bean
	public StringJsonMessageConverter jsonMessageConverter() {
		return new StringJsonMessageConverter();
	}
}
