package wave.domain.post.dto.response;

public record CommentAddResponse(
	Long commentId,
	String comment,
	Long userId,
	String email,
	String nickname
) {
}
