package wave.domain.notification.domain.port.out;

import wave.domain.account.domain.entity.User;

public interface PublishNotificationSendEventPort {

	void publishUnreadNotifications(User user);

}
