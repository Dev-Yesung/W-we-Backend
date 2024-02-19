package wave.domain.notification.adapter.out;

import java.io.IOException;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.adapter.out.broker.RedisNotificationBroker;
import wave.domain.notification.dto.SseConnectionNotification;
import wave.domain.notification.dto.PostNotificationMessage;
import wave.domain.notification.port.out.UpdateNotificationEmitterPort;
import wave.domain.notification.port.out.persistence.NotificationEmitterRepository;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.NotificationException;

@RequiredArgsConstructor
@PersistenceAdapter
public class NotificationEmitterAdapter implements UpdateNotificationEmitterPort {

	private static final long TIME_OUT_VALUE = 10L * (1000 * 60);
	private final NotificationEmitterRepository notificationEmitterRepository;
	private final RedisNotificationBroker redisNotificationBroker;
	private final ObjectMapper objectMapper;

	@Override
	public SseEmitter createNewSubscriber(final User user) {
		final String userId = String.valueOf(user.getId());
		final SseEmitter emitter = new SseEmitter(TIME_OUT_VALUE);
		notificationEmitterRepository.save(userId, emitter);

		String channelName = getChannelName(userId);
		sendConnectionMessage(channelName, userId, emitter);

		final MessageListener messageListener = (message, pattern) -> {
			final PostNotificationMessage postNotificationMessage = serializeMessage(message);
			sendMessageToClient(emitter, userId, postNotificationMessage);
		};

		ChannelTopic channelTopic = ChannelTopic.of(channelName);
		redisNotificationBroker.addMessageListener(messageListener, channelTopic);
		redisNotificationBroker.checkEmitterStatus(emitter, messageListener, userId);

		return emitter;
	}

	private void sendConnectionMessage(String channelName, String userId, SseEmitter emitter) {
		SseConnectionNotification sseConnectionNotification
			= new SseConnectionNotification(channelName, userId, "now connected to notification service.");
		sendMessageToClient(emitter, userId, sseConnectionNotification);
	}

	private PostNotificationMessage serializeMessage(final Message message) {
		try {
			return objectMapper.readValue(message.getBody(), PostNotificationMessage.class);
		} catch (IOException e) {
			throw new NotificationException(ErrorCode.UNABLE_TO_SERIALIZE_NOTIFICATION);
		}
	}

	private void sendMessageToClient(
		final SseEmitter emitter,
		final String userId,
		final Object message
	) {
		try {
			emitter.send(SseEmitter.event()
				.id(userId)
				.name("notification")
				.data(message));
		} catch (IOException e) {
			throw new NotificationException(ErrorCode.INVALID_CONNECTION_FOR_NOTIFICATION, e);
		} finally {
			notificationEmitterRepository.deleteById(userId);
		}
	}

	private String getChannelName(String userId) {
		return "NOTIFICATION:" + userId;
	}
}
