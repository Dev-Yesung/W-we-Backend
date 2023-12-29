package wave.domain.post.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wave.domain.user.domain.User;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("OWN")
@Entity
public class OwnMusicPost extends Post {
	private String filePath;

	public OwnMusicPost(
		String title,
		String contents,
		int waves,
		User user,
		String filePath
	) {
		super(title, contents, waves, user);
		this.filePath = filePath;
	}

	public void updateFilePath(String filePath) {
		validateFilePath(filePath);
		this.filePath = filePath;
	}

	private void validateFilePath(String filePath) {
		String trimmedFilePath = filePath.trim();
		if (trimmedFilePath.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_FILE_PATH);
		}
	}
}
