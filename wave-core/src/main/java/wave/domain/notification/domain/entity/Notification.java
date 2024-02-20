package wave.domain.notification.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.global.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notifications")
@Entity
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;
	private Long postId;

	private String postTitle;
	private String message;
	private boolean isRead;

	public Notification(
		Long userId,
		Long postId,
		String postTitle,
		String message
	) {
		this.userId = userId;
		this.postId = postId;
		this.postTitle = postTitle;
		this.message = message;
		this.isRead = false;
	}

	public String getChannelName() {
		return "NOTIFICATION:" + userId;
	}

	public void readNotification() {
		this.isRead = true;
	}

}
