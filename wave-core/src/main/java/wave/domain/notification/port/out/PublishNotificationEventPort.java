package wave.domain.notification.port.out;

import wave.domain.notification.entity.Notification;

public interface PublishNotificationEventPort {

	void publishUploadMessageToClient(Notification notification);

}
