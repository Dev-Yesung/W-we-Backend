package wave.domain.upload.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.port.out.UpdateImagePort;
import wave.domain.media.domain.port.out.UpdateMusicPort;
import wave.domain.media.dto.FileUploadDto;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class MediaFileUploadService {

	private final UpdateMusicPort updateMusicPort;
	private final UpdateImagePort updateImagePort;

	@KafkaListener(topics = "media_file_upload",
		groupId = "group_media_file_upload",
		containerFactory = "kafkaListenerContainerFactory")
	public void savePostFiles(Object message) {
		MediaFileUploadMessage request = (MediaFileUploadMessage)message;
		saveImageFile(request);
		saveMusicFile(request);
	}

	@KafkaListener(topics = "media_file_delete",
		groupId = "group_media_file_delete",
		containerFactory = "kafkaListenerContainerFactory")
	public void deletePostFiles(Object message) {

	}

	private void saveMusicFile(MediaFileUploadMessage request) {
		FileUploadDto musicFileDto = getMusicFileDto(request);
		updateMusicPort.saveFile(musicFileDto);
	}

	private void saveImageFile(MediaFileUploadMessage request) {
		FileUploadDto imageFileDto = getImageFileDto(request);
		updateImagePort.saveFile(imageFileDto);
	}

	private FileUploadDto getImageFileDto(MediaFileUploadMessage request) {
		Long userId = request.userId();
		Long postId = request.postId();
		MultipartFile imageFile = request.imageFile();

		return getFileUploadDto(userId, postId, imageFile);
	}

	private FileUploadDto getMusicFileDto(MediaFileUploadMessage request) {
		Long userId = request.userId();
		Long postId = request.postId();
		MultipartFile musicFile = request.musicFile();

		return getFileUploadDto(userId, postId, musicFile);
	}

	private FileUploadDto getFileUploadDto(
		Long userId,
		Long postId,
		MultipartFile file
	) {
		return new FileUploadDto(userId, postId, file);
	}

}
