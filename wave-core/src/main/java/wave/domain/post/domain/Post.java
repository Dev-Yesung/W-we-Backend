package wave.domain.post.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.persistence.ElementCollection;
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
import wave.domain.account.domain.entity.User;
import wave.global.BaseEntity;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;
import wave.global.error.exception.EntityException;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "posts")
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String contents;
	private String url;

	@ElementCollection(fetch = FetchType.LAZY)
	private final Map<String, String> likes = new ConcurrentHashMap<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Post(
		String title,
		String contents,
		String url,
		User user
	) {
		this.title = title;
		this.contents = contents;
		this.url = url;
		this.user = user;
	}

	public void updateUrl(String url) {
		validateUrl(url);
		this.url = url;
	}

	public void updateLikes(User user) {
		String email = user.getEmail();
		String nickname = user.getNickname();

		boolean isAlreadyExisted = likes.containsKey(email);
		if (isAlreadyExisted) {
			likes.remove(email);
			return;
		}
		likes.put(email, nickname);
	}

	public int getLikesSize() {
		return likes.size();
	}

	private void validateUrl(String url) {
		if (url.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_URL);
		}
	}

	public void validateAuthority(User user) {
		if (!this.user.equals(user)) {
			throw new EntityException(ErrorCode.NO_AUTHORITY_TO_POST);
		}
	}

	public boolean isOwnMusic() {
		return url.contains("http://localhost:6060");
	}
}
