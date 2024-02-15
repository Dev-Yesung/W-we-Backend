package wave.domain.notification.adapter.out;

import java.io.IOException;
import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.adapter.out.persistence.NotificationJpaRepository;
import wave.domain.notification.dto.NotificationMessage;
import wave.domain.notification.dto.UnreadMessageSendEvent;
import wave.domain.notification.entity.Notification;
import wave.domain.notification.port.out.LoadNotificationPort;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.NotificationException;

@RequiredArgsConstructor
@PersistenceAdapter
public class NotificationPersistenceAdapter implements LoadNotificationPort {

	private final NotificationJpaRepository notificationJpaRepository;

	@Override
	public void sendUnreadMessage(UnreadMessageSendEvent event) {
		final Long userId = getUserId(event);
		final List<Notification> notifications = notificationJpaRepository.findAllByUserId(userId);
		sendUnreadMessage(event, notifications, userId);
	}

	private void sendUnreadMessage(
		UnreadMessageSendEvent event,
		List<Notification> notifications,
		Long userId
	) {
		final SseEmitter emitter = event.emitter();
		notifications.stream()
			.filter(notification -> !notification.isRead())
			.map(NotificationMessage::of)
			.forEach(notificationMessage -> {
				try {
					emitter.send(SseEmitter.event()
						.id(String.valueOf(userId))
						.name("notification")
						.data(notificationMessage));
				} catch (IOException e) {
					throw new NotificationException(ErrorCode.INVALID_CONNECTION_FOR_NOTIFICATION, e);
				}
			});
	}

	private Long getUserId(UnreadMessageSendEvent event) {
		User user = event.user();

		return user.getId();
	}

}
