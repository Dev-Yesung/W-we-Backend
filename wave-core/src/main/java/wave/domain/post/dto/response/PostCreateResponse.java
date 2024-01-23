package wave.domain.post.dto.response;

import wave.domain.media.domain.vo.MediaUploadStatus;
import wave.domain.post.domain.entity.Post;

public record PostCreateResponse(
	Long postId,
	Long userId,
	String uploadStatus
) {

	public static PostCreateResponse of(Post post) {
		Long postId = post.getId();
		Long userId = post.getPostUserId();
		MediaUploadStatus uploadStatus = post.getUploadStatus();
		String status = uploadStatus.name();

		return new PostCreateResponse(postId, userId, status);
	}
}
