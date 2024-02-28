package wave.domain.media.application;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.port.out.PublishImageEventPort;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.dto.request.LoadImageRequest;
import wave.domain.media.dto.response.LoadImageResponse;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@UseCase
public class MediaService {

	private final PublishImageEventPort publishImageEventPort;

	public LoadImageResponse loadImageFile(LoadImageRequest request) {
		Image image = publishImageEventPort.publishLoadImageFileEvent(request);
		String mimeType = image.getMimeType();
		MediaType mediaType = MediaType.parseMediaType(mimeType);
		byte[] fileData = image.getFileData();
		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileData));

		return new LoadImageResponse(mediaType, resource);
	}
}
