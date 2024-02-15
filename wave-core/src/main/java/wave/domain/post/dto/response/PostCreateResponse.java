package wave.domain.post.dto.response;

import wave.domain.post.domain.entity.Post;

public record PostCreateResponse(
	Long postId,
	Long userId,
	String uploadStatus
) {

	public static PostCreateResponse of(Post post) {
		Long postId = post.getId();
		Long userId = post.getUser().getId();
		String status = post.getMediaUrl()
			.getUploadStatus().name();

		return new PostCreateResponse(postId, userId, status);
	}
}
