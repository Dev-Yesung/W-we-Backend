package wave.domain.notification.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import wave.domain.notification.domain.entity.Notification;

public record PostNotificationMessage(
	Long id,
	String channelName,
	Long userId,
	Long postId,
	String postTitle,
	String message,
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	LocalDateTime createdAt
) {

	public static PostNotificationMessage of(Notification notification) {
		Long id = notification.getId();
		String channelName = notification.getChannelName();
		Long userId = notification.getUserId();
		Long postId = notification.getPostId();
		String postTitle = notification.getPostTitle();
		String message = notification.getMessage();
		LocalDateTime createdAt = notification.getUpdatedAt();

		return new PostNotificationMessage(id, channelName, userId, postId, postTitle, message, createdAt);
	}

}
