package wave.domain.post.dto;

import org.springframework.web.multipart.MultipartFile;

import wave.domain.post.domain.entity.Post;
import wave.domain.post.dto.request.MyMusicPostCreateRequest;
import wave.domain.account.domain.entity.User;

public record MyMusicPostDto(
	String title,
	String content,
	MultipartFile imageFile,
	MultipartFile musicFile,
	User user
) {
	public static MyMusicPostDto of(
		MyMusicPostCreateRequest request,
		MultipartFile imageFile,
		MultipartFile musicFile,
		User user
	) {
		String title = request.title();
		String content = request.content();

		return new MyMusicPostDto(title, content, imageFile, musicFile, user);
	}

	public Post toEntity() {
		return new Post(title, content, user);
	}
}
