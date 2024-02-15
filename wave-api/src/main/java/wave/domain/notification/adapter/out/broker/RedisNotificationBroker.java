package wave.domain.notification.adapter.out.broker;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import wave.domain.notification.port.out.persistence.NotificationEmitterRepository;

@RequiredArgsConstructor
@Component
public class RedisNotificationBroker {

	private final NotificationEmitterRepository notificationEmitterRepository;
	private final RedisMessageListenerContainer redisMessageListenerContainer;

	public void addMessageListener(
		MessageListener messageListener,
		ChannelTopic channelTopic
	) {
		redisMessageListenerContainer.addMessageListener(messageListener, channelTopic);
	}

	public void checkEmitterStatus(
		SseEmitter emitter,
		MessageListener messageListener,
		String userId
	) {
		emitter.onCompletion(() -> {
			notificationEmitterRepository.deleteById(userId);
			redisMessageListenerContainer.removeMessageListener(messageListener);
		});
		emitter.onTimeout(() -> {
			notificationEmitterRepository.deleteById(userId);
			redisMessageListenerContainer.removeMessageListener(messageListener);
		});
	}
}
