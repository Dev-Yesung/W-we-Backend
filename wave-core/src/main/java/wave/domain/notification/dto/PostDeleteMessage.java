package wave.domain.notification.dto;

import wave.domain.post.domain.entity.Post;

public record PostDeleteMessage(
	Long userId,
	Long postId,
	String postTitle
) {
	public static PostDeleteMessage of(Post post) {
		Long userId = post.getUser().getId();
		Long postId = post.getId();
		String title = post.getPostContent().getTitle();

		return new PostDeleteMessage(userId, postId, title);
	}
}
