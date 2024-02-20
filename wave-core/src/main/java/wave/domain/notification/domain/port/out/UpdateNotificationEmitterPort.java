package wave.domain.notification.domain.port.out;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import wave.domain.account.domain.entity.User;

public interface UpdateNotificationEmitterPort {

	SseEmitter createNewSubscriber(User user);

}
