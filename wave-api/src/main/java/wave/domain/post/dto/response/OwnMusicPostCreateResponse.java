package wave.domain.post.dto.response;

import wave.domain.music.dto.response.UploadMusicResponse;
import wave.domain.post.domain.OwnMusicPost;
import wave.domain.user.domain.User;

public record OwnMusicPostCreateResponse(
	Long postId,
	Long userId,
	String fileName,
	String extension
) {
	public static OwnMusicPostCreateResponse of(
		OwnMusicPost savedOwnMusicPost,
		UploadMusicResponse uploadMusicResponse
	) {
		Long postId = savedOwnMusicPost.getId();
		User user = savedOwnMusicPost.getUser();
		Long userId = user.getId();
		String fileName = uploadMusicResponse.fileName();
		String extension = uploadMusicResponse.extension();

		return new OwnMusicPostCreateResponse(postId, userId, fileName, extension);
	}
}
