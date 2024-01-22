package wave.domain.post.dto.response;

import wave.domain.post.domain.entity.Post;

public record OtherMusicPostCreateResponse(
	Long postId,
	Long userId,
	String url
) {

	public static OtherMusicPostCreateResponse of(Post post) {
		Long postId = post.getId();
		Long userId = post.getUser().getId();
		String url = post.getUrl();

		return new OtherMusicPostCreateResponse(postId, userId, url);
	}
}
