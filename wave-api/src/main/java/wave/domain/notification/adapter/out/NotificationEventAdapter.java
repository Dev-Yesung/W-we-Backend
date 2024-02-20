package wave.domain.notification.adapter.out;

import java.util.List;

import org.springframework.data.redis.core.RedisOperations;

import lombok.RequiredArgsConstructor;
import wave.domain.notification.dto.PostNotificationMessage;
import wave.domain.notification.domain.entity.Notification;
import wave.domain.notification.domain.port.out.PublishNotificationEventPort;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class NotificationEventAdapter implements PublishNotificationEventPort {

	private final RedisOperations<String, PostNotificationMessage> notificationOperations;

	@Override
	public void publishNotificationToSubscribers(Notification notification) {
		PostNotificationMessage postNotificationMessage = PostNotificationMessage.of(notification);
		String channelName = notification.getChannelName();

		notificationOperations.convertAndSend(channelName, postNotificationMessage);
	}

	@Override
	public void publishNotificationsToSubscribers(List<Notification> notifications) {
		notifications.stream()
			.map(PostNotificationMessage::of)
			.forEach(message -> {
				String channelName = message.channelName();
				notificationOperations.convertAndSend(channelName, message);
			});
	}
}
