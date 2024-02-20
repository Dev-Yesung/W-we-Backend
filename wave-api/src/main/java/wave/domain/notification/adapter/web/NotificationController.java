package wave.domain.notification.adapter.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.application.NotificationSendService;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;

@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@RestController
@WebAdapter
public class NotificationController {

	private final NotificationSendService notificationSendService;

	@GetMapping(value = "/subscribe",
		produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> subscribe(
		@AuthenticationUser User user
	) {
		SseEmitter subscribe = notificationSendService.subscribe(user);

		return ResponseEntity.ok(subscribe);
	}

}
