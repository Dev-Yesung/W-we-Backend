package wave.domain.upload.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.port.out.UpdateMediaPort;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class MediaFileUploadService {

	private final UpdateMediaPort updateMediaPort;

	@KafkaListener(topics = "media_file_upload",
		groupId = "group_media_file_upload",
		containerFactory = "kafkaListenerContainerFactory")
	public void savePostFiles(MediaFileUploadMessage uploadMessage) {
		updateMediaPort.saveFile(uploadMessage);
		// 업로드 작업이 끝나면 포스트 업로드가 끝났다고 메시지 전송
	}

	@KafkaListener(topics = "media_file_delete",
		groupId = "group_media_file_delete",
		containerFactory = "kafkaListenerContainerFactory")
	public void deletePostFiles(Object message) {

	}

}
