package wave.domain.notification.dto;

public record ConnectionMessage(
	String channelName,
	String userId,
	String message
) {
}
