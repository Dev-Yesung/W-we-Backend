package wave.domain.like.domain.entity;

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
@Table(name = "Likes")
@Entity
public class Like extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long postId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private boolean status;

	public Like(Long postId, User user) {
		this.postId = postId;
		this.user = user;
		this.status = true;
	}

	public void changeStatus() {
		this.status = !this.status;
	}

	public void isSameUser(User user) {
		if (!this.user.equals(user)) {
			throw new BusinessException(ErrorCode.NO_AUTHORITY_TO_LIKE);
		}
	}
}
