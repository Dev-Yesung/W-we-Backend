package wave.domain.notification.adapter.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.application.NotificationSenderService;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;

@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@RestController
@WebAdapter
public class NotificationSubscribeController {

	private final NotificationSenderService notificationSenderService;

	@GetMapping(value = "/subscribe",
		produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> subscribe(
		@AuthenticationUser User user
	) {
		SseEmitter subscribe = notificationSenderService.subscribe(user);

		return ResponseEntity.ok(subscribe);
	}

	@PatchMapping("/{notificationId}")
	public ResponseEntity<Void> readNotification(
		@PathVariable Long notificationId,
		@AuthenticationUser User user
	) {
		notificationSenderService.read(notificationId, user);

		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

	@PatchMapping
	public ResponseEntity<Void> readAllNotification(
		@AuthenticationUser User user
	) {
		notificationSenderService.readAll(user);

		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

}
