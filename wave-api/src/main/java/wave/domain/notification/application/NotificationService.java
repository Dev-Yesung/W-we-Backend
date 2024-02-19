package wave.domain.notification.application;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.entity.Notification;
import wave.domain.notification.port.out.LoadNotificationPort;
import wave.domain.notification.port.out.PublishNotificationEventPort;
import wave.domain.notification.port.out.UpdateNotificationEmitterPort;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class NotificationService {

	private final LoadNotificationPort loadNotificationPort;
	private final UpdateNotificationEmitterPort updateNotificationEmitterPort;
	private final PublishNotificationEventPort publishNotificationEventPort;

	public SseEmitter subscribe(final User user) {
		SseEmitter emitter = updateNotificationEmitterPort.createNewSubscriber(user);
		List<Notification> notifications = loadNotificationPort.findAllUnreadNotification(user);
		publishNotificationEventPort.publishNotificationsToSubscribers(notifications);

		return emitter;
	}

}
