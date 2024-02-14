package wave.domain.post.dto.response;

import wave.domain.media.domain.vo.Media;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.vo.PostContent;

public record PostResponse(
	Long postId,
	Long userId,
	PostContent content,
	Media media,
	String nickname,
	int likes
) {
	public static PostResponse from(Post post) {
		Long postId = post.getId();
		Long userId = post.getPostUserId();
		PostContent postContent = post.getPostContent();
		Media media = post.getMedia();
		String nickname = post.getPostUserNickname();
		int likesSize = post.getLikesSize();

		return new PostResponse(postId, userId, postContent, media, nickname, likesSize);
	}
}
