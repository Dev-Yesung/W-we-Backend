package wave.domain.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wave.domain.user.domain.User;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String title;
	String contents;
	int waves;
	String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	User user;

	public Post(
		String title,
		String contents,
		int waves,
		String url,
		User user
	) {
		this.title = title;
		this.contents = contents;
		this.waves = waves;
		this.url = url;
		this.user = user;
	}

	public void updateUrl(String url) {
		validateUrl(url);
		this.url = url;
	}

	private void validateUrl(String url) {
		if (url.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_URL);
		}
	}
}
