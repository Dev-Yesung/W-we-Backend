package wave.domain.post.dto.request;

public record MyMusicPostCreateRequest(
	String artistName,
	String songName,
	String title,
	String descriptions
) {
}
