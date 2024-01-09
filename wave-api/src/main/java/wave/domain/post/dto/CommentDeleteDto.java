package wave.domain.post.dto;

import wave.domain.user.domain.User;

public record CommentDeleteDto(
	long postId,
	long commentId,
	Long userId
) {
	public static CommentDeleteDto of(long postId, long commentId, User user) {
		Long userId = user.getId();

		return new CommentDeleteDto(postId, commentId, userId);
	}
}
