package wave.domain.media.domain.port.out;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.media.dto.FileDeleteDto;
import wave.domain.media.dto.MediaFileUploadMessage;

public interface UpdateMediaPort {

	MediaUrl saveFile(MediaFileUploadMessage mediaFileUploadMessage);

	FileId deleteFile(FileDeleteDto fileDeleteDto);

}
