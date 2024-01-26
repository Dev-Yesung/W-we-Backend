package wave.domain.media.dto.response;

import static wave.domain.media.domain.vo.MediaUploadStatus.*;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUrl;

public record MediaUploadResponse(
	FileId fileId,
	MediaUrl mediaUrl
) {
	public static MediaUrl of(MediaUploadResponse response) {
		String imageUrl = response.imageUrl();
		String musicUrl = response.musicUrl();

		return new MediaUrl(imageUrl, musicUrl, COMPLETED);
	}
}
