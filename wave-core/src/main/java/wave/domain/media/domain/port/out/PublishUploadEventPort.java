package wave.domain.media.domain.port.out;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUploadStatus;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.media.dto.FileDeleteDto;

public interface PublishUploadEventPort {

	void publishUpdateUrlEvent(FileId fileId, MediaUrl mediaUrl);

	void publishUploadStatusEvent(FileId fileId, MediaUploadStatus uploadStatus);

	void publishUploadFileDeleteEvent(FileDeleteDto message);

}
