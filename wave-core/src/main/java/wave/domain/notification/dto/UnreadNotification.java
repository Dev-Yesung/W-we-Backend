package wave.domain.notification.dto;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import wave.domain.account.domain.entity.User;

public record UnreadNotification(
	Long userId,
	SseEmitter emitter
) {
}
