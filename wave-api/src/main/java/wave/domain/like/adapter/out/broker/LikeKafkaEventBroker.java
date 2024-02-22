package wave.domain.like.adapter.out.broker;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wave.domain.like.domain.port.broker.LikeEventBroker;

@Component
public class LikeKafkaEventBroker implements LikeEventBroker {

	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	public LikeKafkaEventBroker(
		@Qualifier("kafkaTemplate") KafkaTemplate<String, Object> kafkaProducerTemplate
	) {
		this.kafkaProducerTemplate = kafkaProducerTemplate;
	}

	@Override
	public CompletableFuture<SendResult<String, Object>> publishMessage(String topic, Object message) {
		return kafkaProducerTemplate.send(topic, message);
	}
}
