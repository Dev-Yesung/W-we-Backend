package wave.domain.media.domain.port.out;

import wave.domain.media.dto.FileDeleteDto;
import wave.domain.media.dto.MediaFileUploadMessage;

public interface UpdateMediaPort {

	void saveFile(MediaFileUploadMessage mediaFileUploadMessage);

	void deleteFile(FileDeleteDto fileDeleteDto);

}
