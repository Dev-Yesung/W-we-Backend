package wave.domain.streaming.dto.request;

public record LoadMusicRequest(
	Long userId,
	Long postId,
	String rangeHeader) {
}
