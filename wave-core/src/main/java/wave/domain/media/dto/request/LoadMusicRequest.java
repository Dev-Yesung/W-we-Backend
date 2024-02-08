package wave.domain.media.dto.request;

public record LoadMusicRequest(
	Long userId,
	Long postId,
	String rangeHeader
) {
}
