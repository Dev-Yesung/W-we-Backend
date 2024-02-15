package wave.domain.media.domain.vo;

import static wave.domain.media.domain.vo.MediaUploadStatus.*;

import org.springframework.util.StringUtils;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MediaUrl {

	private String imageUrl;
	private String musicUrl;

	@Enumerated(EnumType.STRING)
	private MediaUploadStatus uploadStatus;

	public MediaUrl(
		String imageUrl,
		String musicUrl,
		MediaUploadStatus uploadStatus
	) {
		validateImageUrl(imageUrl, uploadStatus);
		validateMusicUrl(musicUrl, uploadStatus);
		this.imageUrl = imageUrl;
		this.musicUrl = musicUrl;
		this.uploadStatus = uploadStatus;
	}

	private void validateImageUrl(
		String imageUrl,
		MediaUploadStatus uploadStatus
	) {
		if (!uploadStatus.equals(SHARED) && imageUrl == null) {
			throw new BusinessException(ErrorCode.INVALID_IMAGE_URL);
		}
	}

	private void validateMusicUrl(String musicUrl, MediaUploadStatus uploadStatus) {
		if (!uploadStatus.equals(PROGRESSED) && !StringUtils.hasText(musicUrl)) {
			throw new BusinessException(ErrorCode.INVALID_MUSIC_URL);
		}
	}
}
