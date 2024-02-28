package wave.domain.upload.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.port.out.PublishUploadEventPort;
import wave.domain.media.domain.port.out.broker.UploadEventBroker;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUploadStatus;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.media.dto.MediaUrlUpdateMessage;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class UploadEventAdapter implements PublishUploadEventPort {

	private final UploadEventBroker uploadEventBroker;

	@Override
	public void publishUpdateUrlEvent(FileId fileId, MediaUrl mediaUrl) {
		MediaUrlUpdateMessage message = new MediaUrlUpdateMessage(fileId, mediaUrl);
		uploadEventBroker.publishMessageToTopic("media_url_update", message);
	}

	@Override
	public void publishUploadStatusEvent(FileId fileId, MediaUploadStatus uploadStatus) {
		MediaFileUploadStatusMessage message = new MediaFileUploadStatusMessage(fileId, uploadStatus);
		uploadEventBroker.publishMessageToTopic("media_upload_status_result", message);
	}

}
