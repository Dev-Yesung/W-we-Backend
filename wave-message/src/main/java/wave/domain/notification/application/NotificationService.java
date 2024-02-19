package wave.domain.notification.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.entity.Notification;
import wave.domain.notification.port.out.PublishNotificationEventPort;
import wave.domain.notification.port.out.UpdateNotificationPort;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class NotificationService {

	private final UpdateNotificationPort updateNotificationPort;
	private final PublishNotificationEventPort publishNotificationEventPort;

	@KafkaListener(topics = "media_upload_status_result",
		groupId = "group_media_upload_status_result",
		containerFactory = "kafkaListenerContainerFactory")
	public void subscribeMediaFileUploadMessage(MediaFileUploadStatusMessage message) {
		Notification notification = updateNotificationPort.saveMessage(message);
		publishNotificationEventPort.publishNotificationToSubscribers(notification);
	}

}
