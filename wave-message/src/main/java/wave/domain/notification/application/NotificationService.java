package wave.domain.notification.application;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.comment.domain.entity.Comment;
import wave.domain.like.domain.entity.Like;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.domain.entity.Notification;
import wave.domain.notification.domain.port.out.LoadNotificationPort;
import wave.domain.notification.domain.port.out.PublishNotificationEventPort;
import wave.domain.notification.domain.port.out.UpdateNotificationPort;
import wave.domain.post.domain.entity.Post;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class NotificationService {

	private final UpdateNotificationPort updateNotificationPort;
	private final LoadNotificationPort loadNotificationPort;
	private final PublishNotificationEventPort publishNotificationEventPort;

	@KafkaListener(topics = "media_upload_status_result",
		groupId = "group_media_upload_status_result",
		containerFactory = "kafkaListenerContainerFactory")
	public void subscribeMediaFileUploadMessage(MediaFileUploadStatusMessage message) {
		Notification notification = updateNotificationPort.saveMediaFileUploadMessage(message);
		publishNotificationEventPort.publishNotificationToSubscribers(notification);
	}

	@KafkaListener(topics = "unread_notification_message",
		groupId = "group_unread_notification_message",
		containerFactory = "kafkaListenerContainerFactory")
	public void subscribeUnreadNotificationMessage(User message) {
		List<Notification> notifications = loadNotificationPort.findAllUnreadNotification(message);
		publishNotificationEventPort.publishNotificationsToSubscribers(notifications);
	}

	@KafkaListener(topics = "new_post_message",
		groupId = "group_new_post_message",
		containerFactory = "kafkaListenerContainerFactory")
	public void subscribeNewSharedPostMusicMessage(Post message) {
		Notification notification = updateNotificationPort.saveNewPostMessage(message);
		publishNotificationEventPort.publishNotificationToSubscribers(notification);
	}

	@KafkaListener(topics = "delete_post_message",
		groupId = "group_delete_post_message",
		containerFactory = "kafkaListenerContainerFactory")
	public void subscribeDeletedPostMessage(Post message) {
		Notification notification = updateNotificationPort.saveDeletePostMessage(message);
		publishNotificationEventPort.publishNotificationToSubscribers(notification);
	}

	@KafkaListener(topics = "update_like_message",
		groupId = "group_update_like_message",
		containerFactory = "kafkaListenerContainerFactory")
	public void subscribeDeletedPostMessage(Like message) {
		Notification notification = updateNotificationPort.saveNewLikeMessage(message);
		publishNotificationEventPort.publishNotificationToSubscribers(notification);
	}

	@KafkaListener(topics = "comment_add_message",
		groupId = "group_comment_add_message",
		containerFactory = "kafkaListenerContainerFactory")
	public void subscribeCommentAddMessage(Comment message) {
		Notification notification = updateNotificationPort.saveNewCommentMessage(message);
		publishNotificationEventPort.publishNotificationToSubscribers(notification);
	}

}
