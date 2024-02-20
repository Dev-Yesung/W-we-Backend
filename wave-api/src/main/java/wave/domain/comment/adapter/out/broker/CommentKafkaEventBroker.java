package wave.domain.comment.adapter.out.broker;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wave.domain.comment.domain.port.out.broker.CommentEventBroker;

@RequiredArgsConstructor
@Component
public class CommentKafkaEventBroker implements CommentEventBroker {

	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	@Override
	public CompletableFuture<SendResult<String, Object>> publishMessage(String topic, Object message) {
		return kafkaProducerTemplate.send(topic, message);
	}
}
