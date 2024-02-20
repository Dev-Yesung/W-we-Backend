package wave.domain.comment.domain.entity;

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
import wave.global.BaseEntity;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long postId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String description;

	public Comment(
		Long postId,
		User user,
		String description
	) {
		this.postId = postId;
		this.user = user;
		this.description = description;
	}

	public void isSameWriter(User user) {
		if (!this.user.equals(user)) {
			throw new BusinessException(ErrorCode.NO_AUTHORITY_TO_COMMENT);
		}
	}
}
