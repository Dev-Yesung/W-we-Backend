package wave.domain.notification.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.port.out.UpdateNotificationEmitterPort;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class NotificationService {

	private final UpdateNotificationEmitterPort updateNotificationEmitterPort;

	public SseEmitter subscribe(final User user) {
		return updateNotificationEmitterPort.createNewSubscriber(user);
	}

}
