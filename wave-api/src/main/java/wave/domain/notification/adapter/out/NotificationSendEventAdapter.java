package wave.domain.notification.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.domain.port.out.PublishNotificationSendEventPort;
import wave.domain.notification.domain.port.out.broker.NotificationSendEventBroker;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class NotificationSendEventAdapter implements PublishNotificationSendEventPort {

	private final NotificationSendEventBroker notificationSendEventBroker;

	@Override
	public void publishUnreadNotifications(User user) {
		notificationSendEventBroker.publishMessage("unread_notification_message", user);
	}

}
