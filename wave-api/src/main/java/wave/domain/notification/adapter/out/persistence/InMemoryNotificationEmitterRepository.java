package wave.domain.notification.adapter.out.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import wave.domain.notification.port.out.persistence.NotificationEmitterRepository;

@Repository
public class InMemoryNotificationEmitterRepository implements NotificationEmitterRepository {

	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEmitter save(String userId, SseEmitter emitter) {
		emitters.put(userId, emitter);

		return emitter;
	}

	public Optional<SseEmitter> findById(String userId) {
		SseEmitter sseEmitter = emitters.get(userId);

		return Optional.ofNullable(sseEmitter);
	}

	public void deleteById(String userId) {
		emitters.remove(userId);
	}

}
