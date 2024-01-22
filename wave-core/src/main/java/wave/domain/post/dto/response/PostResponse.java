package wave.domain.post.dto.response;

import wave.domain.post.domain.entity.Post;
import wave.domain.account.domain.entity.User;

public record PostResponse(
	Long postId,
	Long userId,
	String title,
	String nickname,
	String contents,
	String url,
	int waves
) {
	public static PostResponse of(Post post) {
		Long postId = post.getId();
		User user = post.getUser();
		Long userId = user.getId();
		String title = post.getTitle();
		String nickname = user.getNickname();
		String contents = post.getContents();
		String url = post.getUrl();
		int waves = post.getLikesSize();

		return new PostResponse(
			postId, userId,
			title, nickname, contents,
			url, waves);
	}
}
