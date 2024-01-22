package wave.domain.post.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wave.global.BaseEntity;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long postId;
	private Long userId;
	private String description;
	private String email;
	private String nickname;

	public Comment(
		Long postId,
		Long userId,
		String description,
		String email,
		String nickname
	) {
		this.postId = postId;
		this.userId = userId;
		this.description = description;
		this.email = email;
		this.nickname = nickname;
	}

	public void validateAuthority(Long postId, Long userId) {
		if (!postId.equals(this.postId)) {
			throw new EntityException(ErrorCode.NOT_INCLUDED_COMMENT_TO_POST);
		}
		if (!userId.equals(this.userId)) {
			throw new EntityException(ErrorCode.NO_AUTHORITY_TO_COMMENT);
		}
	}
}
