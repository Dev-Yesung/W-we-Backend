package wave.domain.comment.dto.response;

import wave.domain.comment.domain.entity.Comment;

public record CommentAddResponse(
	Long commentId,
	Long userId
) {
	public static CommentAddResponse of(Comment newComment) {
		Long commentId = newComment.getId();
		Long userId = newComment.getUser().getId();

		return new CommentAddResponse(commentId, userId);
	}
}
