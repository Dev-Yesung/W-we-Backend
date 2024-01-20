package wave.domain.post.dto.request;

public record OtherMusicPostCreateRequest(
	String title,
	String content,
	String url
) {
}
