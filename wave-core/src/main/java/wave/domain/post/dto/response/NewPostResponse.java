package wave.domain.post.dto.response;

import wave.domain.notification.domain.entity.Notification;
import wave.domain.post.domain.entity.Post;

public record NewPostResponse(
	Post savedPost,
	Notification newPostNotification
) {
}
