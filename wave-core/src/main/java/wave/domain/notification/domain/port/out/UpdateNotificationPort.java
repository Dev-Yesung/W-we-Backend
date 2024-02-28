package wave.domain.notification.domain.port.out;

import wave.domain.account.domain.entity.User;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.domain.entity.Notification;
import wave.domain.notification.dto.CommonNotificationMessage;
import wave.domain.notification.dto.NotificationReadMessage;
import wave.domain.notification.dto.PostDeleteMessage;

public interface UpdateNotificationPort {

	Notification saveMediaFileUploadMessage(MediaFileUploadStatusMessage message);

	Notification saveNewPostMessage(CommonNotificationMessage message);

	Notification saveDeletePostMessage(PostDeleteMessage message);

	Notification saveNewLikeMessage(CommonNotificationMessage message);

	Notification saveNewCommentMessage(CommonNotificationMessage message);

	void readNotification(NotificationReadMessage message);

	void readAllNotifications(User message);
}
