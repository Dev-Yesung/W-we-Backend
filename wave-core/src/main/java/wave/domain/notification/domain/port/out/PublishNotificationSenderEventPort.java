package wave.domain.notification.domain.port.out;

import wave.domain.account.domain.entity.User;

public interface PublishNotificationSenderEventPort {

	void publishUnreadNotifications(User user);

	void publishReadNotification(Long notificationId, Long userId);

	void publishReadAllNotifications(User user);
}
