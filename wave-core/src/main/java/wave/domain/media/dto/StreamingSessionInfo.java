package wave.domain.media.dto;

public record StreamingSessionInfo(

	Long postId,
	Long userId,
	long startMilliSec,
	long endMilliSec,
	String ipAddress) {
}
