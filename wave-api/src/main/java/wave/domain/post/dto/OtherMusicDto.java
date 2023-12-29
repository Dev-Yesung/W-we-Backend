package wave.domain.post.dto;

import wave.domain.post.domain.OtherMusicPost;
import wave.domain.post.dto.request.OtherMusicPostCreateRequest;
import wave.domain.user.domain.User;

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

	public OtherMusicPost toEntity() {
		return new OtherMusicPost(title, content, 0, user, url);
	}
}
