package wave.domain.upload.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.port.out.UpdateImagePort;
import wave.domain.media.dto.FileUploadDto;
import wave.domain.media.dto.response.MediaUploadResponse;
import wave.global.common.PersistenceAdapter;

@RequiredArgsConstructor
@PersistenceAdapter
public class ImagePersistenceAdapter implements UpdateImagePort {

	@Override
	public MediaUploadResponse saveFile(FileUploadDto imageFileDto) {
		return null;
	}
}
