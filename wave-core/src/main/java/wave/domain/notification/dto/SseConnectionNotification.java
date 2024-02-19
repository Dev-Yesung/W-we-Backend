package wave.domain.notification.dto;

public record SseConnectionNotification(
	String channelName,
	String userId,
	String message
) {
}
