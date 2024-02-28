package wave.domain.media.dto;

import wave.domain.media.domain.vo.FileId;
import wave.domain.post.domain.entity.Post;

public record FileDeleteDto(
	Long userId,
	Long postId
) {
	public static FileDeleteDto of(Post post) {
		FileId fileId = post.getFileId();
		Long postId = fileId.getPostId();
		Long userId = fileId.getUserId();

		return new FileDeleteDto(userId, postId);
	}
}
