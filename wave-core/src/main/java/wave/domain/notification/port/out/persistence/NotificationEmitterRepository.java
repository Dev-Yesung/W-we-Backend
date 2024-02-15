package wave.domain.notification.port.out.persistence;

import java.util.Optional;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationEmitterRepository {

	SseEmitter save(String userId, SseEmitter emitter);

	Optional<SseEmitter> findById(String userId);

	void deleteById(String userId);

}
