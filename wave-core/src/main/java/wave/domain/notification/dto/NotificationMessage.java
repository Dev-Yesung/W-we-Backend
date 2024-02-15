package wave.domain.notification.dto;

import wave.domain.notification.entity.Notification;
import wave.domain.post.domain.entity.Post;

public record NotificationMessage(
	String channelName,
	String userId,
	String postId,
	String postTitle,
	String message
) {

	public static NotificationMessage of(Notification unreadNotification) {
		String channelName = unreadNotification.getChannelName();
		String userId = String.valueOf(unreadNotification.getUserId());
		String postId = String.valueOf(unreadNotification.getPostId());
		String postTitle = unreadNotification.getPostTitle();
		String message = unreadNotification.getMessage();

		return new NotificationMessage(channelName, userId, postId, postTitle, message);
	}

}
