package wave.domain.like.domain.entity;

import java.util.List;

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
import wave.domain.post.domain.entity.Post;
import wave.global.BaseEntity;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Likes")
@Entity
public class Like extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Like(Post post, User user) {
		this.post = post;
		addToPost(user);
		this.user = user;
	}

	public void addToPost(User user) {
		isSameUser(user);
		List<Like> likes = post.getLikes();
		if (!likes.contains(this)) {
			likes.add(this);
		}
	}

	public void remove(User user) {
		isSameUser(user);
		post.getLikes().remove(this);
	}

	private void isSameUser(User user) {
		if (!this.user.equals(user)) {
			throw new BusinessException(ErrorCode.NO_AUTHORITY_TO_LIKE);
		}
	}

}
