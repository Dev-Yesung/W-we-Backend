package wave.domain.notification.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.domain.port.out.PublishNotificationSendEventPort;
import wave.domain.notification.domain.port.out.UpdateNotificationEmitterPort;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class NotificationSendService {

	private final UpdateNotificationEmitterPort updateNotificationEmitterPort;
	private final PublishNotificationSendEventPort publishNotificationSendEventPort;

	public SseEmitter subscribe(final User user) {
		SseEmitter emitter = updateNotificationEmitterPort.createNewSubscriber(user);
		publishNotificationSendEventPort.publishUnreadNotifications(user);

		return emitter;
	}

}
