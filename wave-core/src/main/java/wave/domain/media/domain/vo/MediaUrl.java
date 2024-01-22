package wave.domain.media.domain.vo;

import java.util.Objects;

import org.springframework.util.StringUtils;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MediaUrl {

	private String imageUrl;
	private String musicUrl;

	public MediaUrl(String imageUrl, String musicUrl) {
		validate(imageUrl, musicUrl);
		this.imageUrl = imageUrl;
		this.musicUrl = musicUrl;
	}

	private void validate(String imageUrl, String musicUrl) {
		if (!(StringUtils.hasText(imageUrl) && StringUtils.hasText(musicUrl))) {
			throw new BusinessException(ErrorCode.INVALID_MEDIA_URL);
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
