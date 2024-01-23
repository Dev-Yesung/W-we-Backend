package wave.domain.media.domain.vo;

import static wave.domain.media.domain.vo.MediaUploadStatus.*;

import java.util.Objects;

import org.springframework.util.StringUtils;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Getter
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MediaUrl mediaUrl = (MediaUrl)o;
		return Objects.equals(imageUrl, mediaUrl.imageUrl) && Objects.equals(musicUrl,
			mediaUrl.musicUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(imageUrl, musicUrl);
	}
}
