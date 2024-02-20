package wave.domain.notification.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.adapter.out.persistence.NotificationJpaRepository;
import wave.domain.notification.adapter.out.persistence.PostJpaRepository;
import wave.domain.notification.domain.entity.Notification;
import wave.domain.notification.domain.port.out.UpdateNotificationPort;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class NotificationPersistenceAdapter implements UpdateNotificationPort {

	private final NotificationJpaRepository notificationJpaRepository;
	private final PostJpaRepository postJpaRepository;

	@Override
	public Notification saveMessage(MediaFileUploadStatusMessage message) {
		FileId fileId = message.fileId();
		Long userId = fileId.getUserId();
		Long postId = fileId.getPostId();
		String postTitle = getPostTitle(postId);
		String notificationMessage = message.uploadStatus().getMessage();
		Notification newNotification = new Notification(userId, postId, postTitle, notificationMessage);

		return notificationJpaRepository.save(newNotification);
	}

	private String getPostTitle(Long postId) {
		return postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST))
			.getPostContent()
			.getTitle();
	}

}
