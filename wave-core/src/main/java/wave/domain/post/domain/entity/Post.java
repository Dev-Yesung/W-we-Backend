package wave.domain.post.domain.entity;

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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.media.domain.vo.MediaUrl;
import wave.global.BaseEntity;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
@Entity
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String contents;
	private MediaUrl mediaUrl;

	@ElementCollection(fetch = FetchType.LAZY)
	private final Map<String, String> likes = new ConcurrentHashMap<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private boolean isUploadCompleted;

	public Post(
		String title,
		String contents,
		User user
	) {
		this(title, contents, null, user, false);
	}

	private Post(
		String title,
		String contents,
		MediaUrl mediaUrl,
		User user,
		boolean isUploadCompleted
	) {
		this.title = title;
		this.contents = contents;
		this.mediaUrl = mediaUrl;
		this.user = user;
		this.isUploadCompleted = isUploadCompleted;
	}

	public void updateMediaUrl(MediaUrl mediaUrl) {
		this.mediaUrl = mediaUrl;
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

	public void validateAuthority(User user) {
		if (!this.user.equals(user)) {
			throw new BusinessException(ErrorCode.NO_AUTHORITY_TO_POST);
		}
	}

	public int getLikesSize() {
		return likes.size();
	}

	public Long getPostUserId() {
		return user.getId();
	}
}
