package wave.domain.post.dto;

import static wave.domain.media.domain.vo.MediaUploadStatus.*;

import wave.domain.account.domain.entity.User;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.vo.PostContent;
import wave.domain.post.dto.request.SharedMusicPostCreateRequest;

public record SharedMusicPostDto(
	String artistName,
	String songName,
	String title,
	String descriptions,
	String musicUrl,
	User user
) {
	public static SharedMusicPostDto from(
		SharedMusicPostCreateRequest request,
		User user
	) {
		String artistName = request.artistName();
		String songName = request.songName();
		String title = request.title();
		String content = request.descriptions();
		String musicUrl = request.musicUrl();

		return new SharedMusicPostDto(artistName, songName, title, content, musicUrl, user);
	}

	public static Post toEntity(SharedMusicPostDto request) {
		String artistName = request.artistName();
		String songName = request.songName();
		String title = request.title();
		String descriptions = request.descriptions();
		PostContent postContent = new PostContent(artistName, songName, title, descriptions);

		String musicUrl = request.musicUrl();
		MediaUrl mediaUrl = new MediaUrl(null, musicUrl, SHARED);
		User user = request.user();

		return new Post(postContent, mediaUrl, user);
	}
}
