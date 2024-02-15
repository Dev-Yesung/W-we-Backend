package wave.domain.notification.port.out;

import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.entity.Notification;

public interface UpdateNotificationPort {

	Notification saveMessage(MediaFileUploadStatusMessage message);

}
