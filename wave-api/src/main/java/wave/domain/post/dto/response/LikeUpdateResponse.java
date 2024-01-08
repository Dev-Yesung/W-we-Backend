package wave.domain.post.dto.response;

public record LikeUpdateResponse(
	long postId,
	String email,
	String nickname
) {
}
