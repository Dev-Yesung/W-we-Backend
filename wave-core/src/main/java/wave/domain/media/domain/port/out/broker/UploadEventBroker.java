package wave.domain.media.domain.port.out.broker;

import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.media.dto.MediaUrlUpdateMessage;

public interface UploadEventBroker {

	void publishUploadStatusEvent(MediaFileUploadStatusMessage message);

	void publishUrlUpdateEvent(MediaUrlUpdateMessage message);
}
