package wave.domain.post.dto.request;

public record SharedMusicPostCreateRequest(
	String artistName,
	String songName,
	String title,
	String descriptions,
	String musicUrl
) {
}
