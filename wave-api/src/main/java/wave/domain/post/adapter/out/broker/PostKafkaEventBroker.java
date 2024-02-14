package wave.domain.post.adapter.out.broker;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.domain.post.domain.port.out.PostEventBroker;

@RequiredArgsConstructor
@Component
public class PostKafkaEventBroker implements PostEventBroker {

	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	@Override
	public CompletableFuture<SendResult<String, Object>> publishMusicUploadEvent(
		MediaFileUploadMessage mediaFileUploadMessage
	) {
		String topic = mediaFileUploadMessage.topic();

		return kafkaProducerTemplate.send(topic, mediaFileUploadMessage);
	}
}
