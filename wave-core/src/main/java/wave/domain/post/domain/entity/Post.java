package wave.domain.post.domain.entity;

import jakarta.persistence.Embedded;
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
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.post.domain.vo.PostContent;
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

	@Embedded
	private PostContent postContent;

	@Embedded
	private MediaUrl mediaUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Post(
		PostContent postContent,
		MediaUrl mediaUrl,
		User user
	) {
		updateMediaUrl(mediaUrl);
		this.postContent = postContent;
		this.mediaUrl = mediaUrl;
		this.user = user;
	}

	public void updateMediaUrl(MediaUrl mediaUrl) {
		if (mediaUrl == null) {
			throw new BusinessException(ErrorCode.INVALID_MUSIC_URL);
		}

		this.mediaUrl = mediaUrl;
	}

	public void validateAuthority(User user) {
		if (!this.user.equals(user)) {
			throw new BusinessException(ErrorCode.NO_AUTHORITY_TO_POST);
		}
	}

	public FileId getFileId() {
		Long userId = user.getId();

		return new FileId(userId, this.id);
	}

	public void isAuthor(User user) {
		if (!this.user.equals(user)) {
			throw new BusinessException(ErrorCode.NO_AUTHORITY_TO_POST);
		}
	}
}
