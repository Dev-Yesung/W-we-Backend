package wave.domain.comment.dto.response;

import wave.domain.comment.domain.entity.Comment;

public record CommentDeleteResponse(
	long commentId,
	long postId,
	long userId
) {

	public static CommentDeleteResponse of(Comment comment) {
		Long commentId = comment.getId();
		Long postId = comment.getPostId();
		Long userId = comment.getUser().getId();

		return new CommentDeleteResponse(commentId, postId, userId);
	}

}
