package wave.domain.post.dto;

import static wave.domain.media.domain.vo.MediaUploadStatus.*;

import org.springframework.web.multipart.MultipartFile;

import wave.domain.account.domain.entity.User;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.vo.PostContent;
import wave.domain.post.dto.request.MyMusicPostCreateRequest;

public record MyMusicPostDto(
	String artistName,
	String songName,
	String title,
	String descriptions,
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
		String artistName = request.artistName();
		String songName = request.songName();
		String title = request.title();
		String descriptions = request.descriptions();

		return new MyMusicPostDto(artistName, songName, title, descriptions, imageFile, musicFile, user);
	}

	public static Post to(MyMusicPostDto myMusicPostDto) {
		String artistName = myMusicPostDto.artistName();
		String songName = myMusicPostDto.songName();
		String title = myMusicPostDto.title();
		String descriptions = myMusicPostDto.descriptions();
		PostContent postContent
			= new PostContent(artistName, songName, title, descriptions);
		MediaUrl mediaUrl = new MediaUrl("", "", PROGRESSED);
		User user = myMusicPostDto.user();

		return new Post(postContent, mediaUrl, user);
	}
}
