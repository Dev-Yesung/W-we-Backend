package wave.domain.post.dto.response;

import wave.domain.post.domain.Post;

public record OwnMusicPostCreateResponse(
	Long postId,
	Long userId,
	String url
) {

	public static OwnMusicPostCreateResponse of(Post post) {
		Long postId = post.getId();
		Long userId = post.getUser().getId();
		String url = post.getUrl();

		return new OwnMusicPostCreateResponse(postId, userId, url);
	}
}
