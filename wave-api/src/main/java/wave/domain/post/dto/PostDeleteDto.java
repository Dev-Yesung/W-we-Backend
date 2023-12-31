package wave.domain.post.dto;

import wave.domain.user.domain.User;

public record PostDeleteDto(
	Long postId,
	Long userId
) {
	public static PostDeleteDto of(
		Long postId,
		User user
	) {
		Long userId = user.getId();

		return new PostDeleteDto(postId, userId);
	}
}
