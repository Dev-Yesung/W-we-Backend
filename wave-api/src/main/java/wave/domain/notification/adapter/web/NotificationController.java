package wave.domain.notification.adapter.web;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.application.NotificationService;
import wave.domain.notification.dto.UnreadMessageSendEvent;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;

@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@RestController
@WebAdapter
public class NotificationController {

	private final ApplicationEventPublisher eventPublisher;
	private final NotificationService notificationService;

	@GetMapping(value = "/subscribe",
		produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> subscribe(
		@AuthenticationUser User user
	) {
		SseEmitter subscribe = notificationService.subscribe(user);
		publishEventToCheckUnreadMessage(user, subscribe);

		return ResponseEntity.ok(subscribe);
	}

	@Async
	@EventListener
	public void sendUnreadMessage(UnreadMessageSendEvent unreadMessageSendEvent) {
		notificationService.sendUnreadMessage(unreadMessageSendEvent);
	}

	private void publishEventToCheckUnreadMessage(User user, SseEmitter subscribe) {
		UnreadMessageSendEvent unreadMessageSendEvent = new UnreadMessageSendEvent(user, subscribe);
		eventPublisher.publishEvent(unreadMessageSendEvent);
	}

}
