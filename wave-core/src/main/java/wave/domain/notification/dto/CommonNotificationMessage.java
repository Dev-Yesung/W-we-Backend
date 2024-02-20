package wave.domain.notification.dto;

public record CommonNotificationMessage(
	Long userId,
	Long postId
) {
}
