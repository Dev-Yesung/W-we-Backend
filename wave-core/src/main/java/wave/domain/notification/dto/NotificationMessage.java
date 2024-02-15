package wave.domain.notification.dto;

import wave.domain.notification.entity.Notification;
import wave.domain.post.domain.entity.Post;

public record NotificationMessage(
	String channelName,
	Long userId,
	Long postId,
	String postTitle,
	String message
) {
	public static NotificationMessage of(
		Notification notification,
		Post post
	) {
		String channelName = notification.getChannelName();
		Long userId = notification.getUserId();
		Long postId = notification.getPostId();
		String postTitle = post.getPostContent().getTitle();
		String message = notification.getMessage();

		return new NotificationMessage(channelName, userId, postId, postTitle, message);
	}
}
