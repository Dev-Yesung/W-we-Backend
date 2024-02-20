package wave.domain.notification.domain.port.out;

import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.domain.entity.Notification;

public interface UpdateNotificationPort {

	Notification saveMessage(MediaFileUploadStatusMessage message);

}
