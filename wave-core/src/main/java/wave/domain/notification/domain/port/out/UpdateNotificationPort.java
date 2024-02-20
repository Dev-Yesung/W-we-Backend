package wave.domain.notification.domain.port.out;

import wave.domain.comment.domain.entity.Comment;
import wave.domain.like.domain.entity.Like;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.domain.entity.Notification;
import wave.domain.post.domain.entity.Post;

public interface UpdateNotificationPort {

	Notification saveMediaFileUploadMessage(MediaFileUploadStatusMessage message);

	Notification saveNewPostMessage(Post message);

	Notification saveDeletePostMessage(Post message);

	Notification saveNewLikeMessage(Like message);

	Notification saveNewCommentMessage(Comment message);
}
