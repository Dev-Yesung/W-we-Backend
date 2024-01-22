package wave.domain.post.infra;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wave.domain.media.dto.MediaFileUploadMessage;

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
