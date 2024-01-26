package wave.domain.media.domain.port.out;

import wave.domain.media.dto.FileUploadDto;
import wave.domain.media.dto.response.MediaUploadResponse;

public interface UpdateImagePort {

	MediaUploadResponse saveFile(FileUploadDto imageFileDto);
}
