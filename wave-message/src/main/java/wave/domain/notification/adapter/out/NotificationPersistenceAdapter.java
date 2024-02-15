package wave.domain.notification.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.adapter.out.persistence.NotificationJpaRepository;
import wave.domain.notification.entity.Notification;
import wave.domain.notification.port.out.UpdateNotificationPort;
import wave.global.common.PersistenceAdapter;

@RequiredArgsConstructor
@PersistenceAdapter
public class NotificationPersistenceAdapter implements UpdateNotificationPort {

	private final NotificationJpaRepository notificationJpaRepository;

	@Override
	public Notification saveMessage(MediaFileUploadStatusMessage message) {
		FileId fileId = message.fileId();
		Long userId = fileId.getUserId();
		Long postId = fileId.getPostId();
		String notificationMessage = message.uploadStatus().getMessage();
		Notification newNotification = new Notification(userId, postId, notificationMessage);

		return notificationJpaRepository.save(newNotification);
	}

}
