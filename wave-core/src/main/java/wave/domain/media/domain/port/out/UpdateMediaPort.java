package wave.domain.media.domain.port.out;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.media.dto.FileDeleteDto;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.domain.media.dto.StreamingSessionInfo;

public interface UpdateMediaPort {

	MediaUrl saveFile(MediaFileUploadMessage mediaFileUploadMessage);

	FileId deleteFile(FileDeleteDto fileDeleteDto);

	void saveStreamingSession(StreamingSessionInfo sessionInfo);

}
