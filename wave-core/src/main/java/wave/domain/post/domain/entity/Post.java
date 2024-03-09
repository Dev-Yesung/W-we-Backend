package wave.domain.post.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.like.domain.entity.Like;
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

	@OneToMany(mappedBy = "post",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<Like> likes = new ArrayList<>();

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

	public FileId getFileId() {
		Long userId = user.getId();

		return new FileId(userId, this.id);
	}

	public void isAuthor(User user) {
		if (!this.user.getId().equals(user.getId())) {
			throw new BusinessException(ErrorCode.NO_AUTHORITY_TO_POST);
		}
	}

	public int getLikeSize() {
		return likes.size();
	}

}
