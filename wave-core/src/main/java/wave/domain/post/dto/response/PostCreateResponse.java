package wave.domain.post.dto.response;

import wave.domain.post.domain.entity.Post;

public record PostCreateResponse(
	Long postId,
	Long userId,
	boolean isUploadCompleted
) {

	public static PostCreateResponse of(Post post) {
		Long postId = post.getId();
		Long userId = post.getPostUserId();
		boolean isUploadCompleted = post.isUploadCompleted();

		return new PostCreateResponse(postId, userId, isUploadCompleted);
	}
}
