package wave.domain.comment.dto.response;

import wave.domain.comment.domain.entity.Comment;

public record CommentResponse(
	Long postId,
	Long commentId,
	String nickname,
	String email,
	String description
) {

	public static CommentResponse of(Comment comment) {
		Long postId = comment.getPostId();
		Long commentId = comment.getId();
		String nickname = comment.getUser().getNickname();
		String email = comment.getUser().getEmail();
		String description = comment.getDescription();

		return new CommentResponse(postId, commentId, nickname, email, description);
	}

}
