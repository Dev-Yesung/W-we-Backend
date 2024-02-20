package wave.domain.upload.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.port.out.PublishUploadEventPort;
import wave.domain.media.domain.port.out.UpdateMediaPort;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUploadStatus;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class MediaFileUploadService {

	private final UpdateMediaPort updateMediaPort;
	private final PublishUploadEventPort publishUploadEventPort;

	// todo: 이 부분 리팩토링 필요 -> 미디어 서버에서 데이터를 업데이트하는 방식으로 변경하기
	@KafkaListener(topics = "media_file_upload",
		groupId = "group_media_file_upload",
		containerFactory = "kafkaListenerContainerFactory")
	public void savePostFiles(MediaFileUploadMessage uploadMessage) {
		MediaUrl mediaUrl = updateMediaPort.saveFile(uploadMessage);

		FileId fileId = uploadMessage.fileId();
		MediaUploadStatus uploadStatus = mediaUrl.getUploadStatus();
		publishUploadEventPort.publishUpdateUrlEvent(fileId, mediaUrl);
		publishUploadEventPort.publishUploadStatusEvent(fileId, uploadStatus);
	}

	@KafkaListener(topics = "media_file_delete",
		groupId = "group_media_file_delete",
		containerFactory = "kafkaListenerContainerFactory")
	public void deletePostFiles(Object message) {

	}

}
