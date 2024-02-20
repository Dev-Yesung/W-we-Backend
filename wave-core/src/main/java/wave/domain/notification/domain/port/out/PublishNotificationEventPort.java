package wave.domain.notification.domain.port.out;

import java.util.List;

import wave.domain.notification.domain.entity.Notification;

public interface PublishNotificationEventPort {

	void publishNotificationToSubscribers(Notification notification);

	void publishNotificationsToSubscribers(List<Notification> notifications);

}
