package wave.domain.notification.adapter.out;

import org.springframework.data.redis.core.RedisOperations;

import lombok.RequiredArgsConstructor;
import wave.domain.notification.dto.NotificationMessage;
import wave.domain.notification.entity.Notification;
import wave.domain.notification.port.out.PublishNotificationEventPort;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class NotificationEventAdapter implements PublishNotificationEventPort {

	private final RedisOperations<String, NotificationMessage> notificationOperations;

	@Override
	public void publishUploadMessageToClient(Notification notification) {
		NotificationMessage notificationMessage = NotificationMessage.of(notification);
		String channelName = notification.getChannelName();

		notificationOperations.convertAndSend(channelName, notificationMessage);
	}

}
