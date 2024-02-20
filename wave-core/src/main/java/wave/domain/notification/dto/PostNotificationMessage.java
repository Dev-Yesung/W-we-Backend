package wave.domain.notification.dto;

import java.time.LocalDateTime;

import wave.domain.notification.domain.entity.Notification;

public record PostNotificationMessage(
	String channelName,
	String userId,
	String postId,
	String postTitle,
	String message,
	LocalDateTime createdAt
) {

	public static PostNotificationMessage of(Notification notification) {
		String channelName = notification.getChannelName();
		String userId = String.valueOf(notification.getUserId());
		String postId = String.valueOf(notification.getPostId());
		String postTitle = notification.getPostTitle();
		String message = notification.getMessage();
		LocalDateTime createdAt = notification.getUpdatedAt();

		return new PostNotificationMessage(channelName, userId, postId, postTitle, message, createdAt);
	}

}
