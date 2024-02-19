package wave.domain.like.dto.response;

public record LikeUpdateResponse(
	long postId,
	String email,
	String nickname
) {
}
