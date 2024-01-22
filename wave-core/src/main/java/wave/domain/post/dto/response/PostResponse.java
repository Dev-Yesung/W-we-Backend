package wave.domain.post.dto.response;

import wave.domain.post.domain.entity.Post;

public record PostResponse(
	Long postId,
	Long userId,
	String title,
	String nickname,
	String contents,
	String imageUrl,
	String musicUrl,
	int likes
) {
	public static PostResponse of(Post post) {
		Long postId = post.getId();
		Long userId = post.getPostUserId();
		String title = post.getTitle();
		String nickname = post.getPostUserNickname();
		String contents = post.getContents();
		String imageUrl = post.getImageUrl();
		String musicUrl = post.getMusicUrl();
		int likes = post.getLikesSize();

		return new PostResponse(
			postId, userId,
			title, nickname, contents,
			imageUrl, musicUrl, likes);
	}
}
