package wave.domain.post.dto.response;

import wave.domain.post.domain.OtherMusicPost;
import wave.domain.user.domain.User;

public record OtherMusicPostCreateResponse(
	Long postId,
	Long userId,
	String url
) {
	public static OtherMusicPostCreateResponse of(OtherMusicPost otherMusicPost) {
		Long postId = otherMusicPost.getId();
		User user = otherMusicPost.getUser();
		Long userId = user.getId();
		String url = otherMusicPost.getUrl();

		return new OtherMusicPostCreateResponse(postId, userId, url);
	}
}
