package wave.domain.post.dto;

import org.springframework.web.multipart.MultipartFile;

import wave.domain.post.domain.OwnMusicPost;
import wave.domain.post.dto.request.OwnMusicPostCreateRequest;
import wave.domain.user.domain.User;

public record OwnMusicDto(
	String title,
	String content,
	MultipartFile ownMusic,
	User user
) {
	public static OwnMusicDto of(
		OwnMusicPostCreateRequest request,
		MultipartFile ownMusic,
		User user
	) {
		String title = request.title();
		String content = request.content();

		return new OwnMusicDto(title, content, ownMusic, user);
	}

	public OwnMusicPost toEntity() {
		return new OwnMusicPost(title, content, 0, user, "");
	}
}
