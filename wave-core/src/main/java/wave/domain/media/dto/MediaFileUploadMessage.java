package wave.domain.media.dto;

import org.springframework.web.multipart.MultipartFile;

import wave.domain.post.domain.entity.Post;
import wave.domain.post.dto.MyMusicPostDto;

public record MediaFileUploadMessage(

	String topic,
	Long userId,
	Long postId,
	MultipartFile imageFile,
	MultipartFile musicFile
) {

	public static MediaFileUploadMessage of(Post post, MyMusicPostDto myMusicPostDto) {
		Long userId = post.getPostUserId();
		Long postId = post.getId();
		MultipartFile imageFile = myMusicPostDto.imageFile();
		MultipartFile musicFile = myMusicPostDto.musicFile();

		return new MediaFileUploadMessage("media_file_upload", userId, postId, imageFile, musicFile);
	}
}
