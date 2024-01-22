package wave.domain.media.dto.response;

import wave.domain.media.domain.vo.MediaUrl;

public record MediaUploadResponse(
	Long userId,
	Long postId,
	String imageUrl,
	String musicUrl
) {
	public static MediaUrl of(MediaUploadResponse response) {
		String imageUrl = response.imageUrl();
		String musicUrl = response.musicUrl();

		return new MediaUrl(imageUrl, musicUrl);
	}
}
