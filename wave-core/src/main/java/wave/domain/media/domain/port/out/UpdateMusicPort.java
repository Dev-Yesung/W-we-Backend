package wave.domain.media.domain.port.out;

import wave.domain.media.dto.FileDeleteDto;
import wave.domain.media.dto.FileUploadDto;

public interface UpdateMusicPort {

	void saveFile(FileUploadDto fileUploadDto);

	void deleteFile(FileDeleteDto fileDeleteDto);
}
