package wave.domain.notification.domain.port.out;

import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.domain.entity.Notification;
import wave.domain.notification.dto.CommonNotificationMessage;

public interface UpdateNotificationPort {

	Notification saveMediaFileUploadMessage(MediaFileUploadStatusMessage message);

	Notification saveNewPostMessage(CommonNotificationMessage message);

	Notification saveDeletePostMessage(CommonNotificationMessage message);

	Notification saveNewLikeMessage(CommonNotificationMessage message);

	Notification saveNewCommentMessage(CommonNotificationMessage message);
}
