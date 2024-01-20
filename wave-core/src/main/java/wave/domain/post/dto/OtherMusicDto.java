package wave.domain.post.dto;

import wave.domain.post.domain.Post;
import wave.domain.post.dto.request.OtherMusicPostCreateRequest;
import wave.domain.account.domain.entity.User;

public record OtherMusicDto(
	String title,
	String content,
	String url,
	User user
) {
	public static OtherMusicDto of(OtherMusicPostCreateRequest request, User user) {
		String title = request.title();
		String content = request.content();
		String link = request.url();

		return new OtherMusicDto(title, content, link, user);
	}

	public Post toEntity() {
		return new Post(title, content, url, user);
	}
}
