package wave.domain.notification.port.out;

import java.util.List;

import wave.domain.notification.entity.Notification;

public interface PublishNotificationEventPort {

	void publishNotificationToSubscribers(Notification notification);

	void publishNotificationsToSubscribers(List<Notification> notifications);

}
