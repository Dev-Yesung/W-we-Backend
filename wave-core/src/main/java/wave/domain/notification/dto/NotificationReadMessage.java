package wave.domain.notification.dto;

public record NotificationReadMessage(
	Long notificationId,
	Long userId
) {
}
