package wave.domain.image.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.entity.ImageFile;
import wave.domain.media.domain.port.out.LoadMediaPort;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.dto.request.LoadPostImageRequest;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@UseCase
public class ImageService {

	private final LoadMediaPort loadMediaPort;

	@SendTo
	@KafkaListener(topics = "load_image_requests",
		groupId = "group_load_image_requests",
		containerFactory = "kafkaListenerContainerFactory")
	public Image loadImageFile(LoadPostImageRequest request) {
		ImageFile imageFile = loadMediaPort.loadImageFile(request);

		return imageFile.getImage();
	}

}
