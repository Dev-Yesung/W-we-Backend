package wave.domain.post.dto;

import static wave.domain.media.domain.vo.MediaUploadStatus.*;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import wave.domain.account.domain.entity.User;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.domain.vo.Media;
import wave.domain.media.domain.vo.MediaMultipartFile;
import wave.domain.media.domain.vo.Music;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.vo.PostContent;
import wave.domain.post.dto.request.MyMusicPostCreateRequest;
import wave.global.utils.FileUtils;
import wave.global.utils.MultipartFileUtils;

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

	public static Post toPost(MyMusicPostDto myMusicPostDto) {
		String artistName = myMusicPostDto.artistName();
		String songName = myMusicPostDto.songName();
		String title = myMusicPostDto.title();
		String descriptions = myMusicPostDto.descriptions();
		PostContent postContent
			= new PostContent(artistName, songName, title, descriptions);
		Media media = new Media("", "", PROGRESSED);
		User user = myMusicPostDto.user();

		return new Post(postContent, media, user);
	}

	public static Image toImage(MyMusicPostDto myMusicPostDto) {
		MultipartFile imageFile = myMusicPostDto.imageFile();
		String fileName = MultipartFileUtils.getPureFileName(imageFile);
		String fileExtension = MultipartFileUtils.getFileExtension(imageFile);
		String mimeType = imageFile.getContentType();
		long fileSize = MultipartFileUtils.getFileSize(imageFile);
		byte[] imageData = MultipartFileUtils.getBytes(imageFile);

		return new Image(fileName, fileExtension, mimeType, fileSize, "", imageData);
	}

	public static Music toMusic(MyMusicPostDto myMusicPostDto) {
		MultipartFile musicFile = myMusicPostDto.musicFile();
		String fileName = MultipartFileUtils.getPureFileName(musicFile);
		String fileExtension = MultipartFileUtils.getFileExtension(musicFile);
		String mimeType = musicFile.getContentType();
		long fileSize = MultipartFileUtils.getFileSize(musicFile);
		byte[] musicData = MultipartFileUtils.getBytes(musicFile);

		return new Music(fileName, fileExtension, mimeType, fileSize, "", musicData);
	}
}
