package wave.domain.notification.adapter.out;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.domain.port.out.PublishNotificationSenderEventPort;
import wave.domain.notification.domain.port.out.broker.NotificationSendEventBroker;
import wave.domain.notification.dto.NotificationReadMessage;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
@EventAdapter
public class NotificationSubscribeEventAdapter implements PublishNotificationSenderEventPort {

	private final NotificationSendEventBroker notificationSendEventBroker;

	@Override
	public void publishUnreadNotifications(User user) {
		notificationSendEventBroker.publishMessage("unread_notification_message", user);
	}

	@Override
	public void publishReadNotification(Long notificationId, Long userId) {
		NotificationReadMessage message = new NotificationReadMessage(notificationId, userId);
		notificationSendEventBroker.publishMessage("read_notification_message", message);
	}

	@Override
	public void publishReadAllNotifications(User user) {
		notificationSendEventBroker.publishMessage("read_all_notification_message", user);
	}

}
