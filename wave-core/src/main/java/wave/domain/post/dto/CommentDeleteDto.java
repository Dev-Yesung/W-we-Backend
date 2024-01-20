package wave.domain.post.dto;

import wave.domain.account.domain.entity.User;

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
