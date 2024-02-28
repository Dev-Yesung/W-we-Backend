package wave.domain.post.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import wave.domain.account.domain.entity.User;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.vo.PostContent;

public record PostResponse(
	Long postId,
	Long userId,
	PostContent content,
	MediaUrl mediaUrl,
	String nickname,
	int likeSize,
	@JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
	LocalDateTime createdAt
) {
	public static PostResponse of(Post post) {
		User user = post.getUser();
		Long postId = post.getId();
		Long userId = user.getId();
		PostContent postContent = post.getPostContent();
		MediaUrl mediaUrl = post.getMediaUrl();
		String nickname = user.getNickname();
		int likeSize = post.getLikeSize();
		LocalDateTime createdAt = post.getCreatedAt();

		return new PostResponse(postId, userId, postContent, mediaUrl, nickname, likeSize, createdAt);
	}
}
