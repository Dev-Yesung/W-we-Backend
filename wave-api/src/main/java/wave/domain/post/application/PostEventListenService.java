package wave.domain.post.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.media.dto.MediaUrlUpdateMessage;
import wave.domain.post.domain.port.out.UpdatePostPort;
import wave.global.common.UseCase;

@Transactional
@RequiredArgsConstructor
@UseCase
public class PostEventListenService {

	private final UpdatePostPort updatePostPort;

	@KafkaListener(topics = "media_url_update",
		groupId = "group_media_url_update",
		containerFactory = "kafkaListenerContainerFactory")
	public void subscribeMediaFileUploadMessage(MediaUrlUpdateMessage message) {
		updatePostPort.updateMusicUploadUrl(message);
	}

}
