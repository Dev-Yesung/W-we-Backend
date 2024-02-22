package wave.domain.post.adapter.out.broker;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import wave.domain.post.domain.port.out.broker.PostEventBroker;

@Component
public class PostKafkaEventBroker implements PostEventBroker {

	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	public PostKafkaEventBroker(
		@Qualifier("kafkaTemplate") KafkaTemplate<String, Object> kafkaProducerTemplate
	) {
		this.kafkaProducerTemplate = kafkaProducerTemplate;
	}

	@Override
	public CompletableFuture<SendResult<String, Object>> publishMessage(String topic, Object message) {
		return kafkaProducerTemplate.send(topic, message);
	}
}
