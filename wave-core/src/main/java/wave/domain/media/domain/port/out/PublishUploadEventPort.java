package wave.domain.media.domain.port.out;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUploadStatus;
import wave.domain.media.domain.vo.MediaUrl;

public interface PublishUploadEventPort {

	void publishUpdateUrlEvent(FileId fileId, MediaUrl mediaUrl);

	void publishUploadStatusEvent(FileId fileId, MediaUploadStatus uploadStatus);

}
