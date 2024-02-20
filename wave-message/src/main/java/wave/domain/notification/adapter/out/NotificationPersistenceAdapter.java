package wave.domain.notification.adapter.out;

import java.util.List;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.adapter.out.persistence.AccountJpaRepository;
import wave.domain.notification.adapter.out.persistence.NotificationJpaRepository;
import wave.domain.notification.adapter.out.persistence.PostJpaRepository;
import wave.domain.notification.domain.entity.Notification;
import wave.domain.notification.domain.port.out.LoadNotificationPort;
import wave.domain.notification.domain.port.out.UpdateNotificationPort;
import wave.domain.notification.dto.CommonNotificationMessage;
import wave.domain.notification.dto.NotificationReadMessage;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class NotificationPersistenceAdapter implements UpdateNotificationPort, LoadNotificationPort {

	private final AccountJpaRepository accountJpaRepository;
	private final PostJpaRepository postJpaRepository;
	private final NotificationJpaRepository notificationJpaRepository;

	@Override
	public Notification saveMediaFileUploadMessage(MediaFileUploadStatusMessage message) {
		FileId fileId = message.fileId();
		Long userId = fileId.getUserId();
		Long postId = fileId.getPostId();
		String postTitle = getPostTitle(postId);
		String notificationMessage = message.uploadStatus().getMessage();
		Notification newNotification = new Notification(userId, postId, postTitle, notificationMessage);

		return notificationJpaRepository.save(newNotification);
	}

	@Override
	public Notification saveNewPostMessage(CommonNotificationMessage message) {
		Long userId = message.userId();
		Long postId = message.postId();
		String postTitle = getPostTitle(postId);
		Notification notification = new Notification(userId, postId, postTitle, "포스트 등록을 완료했습니다.");

		return notificationJpaRepository.save(notification);
	}

	@Override
	public Notification saveDeletePostMessage(CommonNotificationMessage message) {
		Long userId = message.userId();
		Long postId = message.postId();
		String postTitle = getPostTitle(postId);
		Notification notification = new Notification(userId, postId, postTitle, "포스트 삭제를 완료했습니다.");

		return notificationJpaRepository.save(notification);
	}

	@Override
	public Notification saveNewLikeMessage(CommonNotificationMessage message) {
		Long userId = message.userId();
		Long postId = message.postId();
		String postTitle = getPostTitle(postId);
		String nickname = getUserNickname(userId);
		Notification notification = new Notification(userId, postId, postTitle, nickname + "님이 이 음악을 추천합니다.");

		return notificationJpaRepository.save(notification);
	}

	@Override
	public Notification saveNewCommentMessage(CommonNotificationMessage message) {
		Long userId = message.userId();
		Long postId = message.postId();
		String postTitle = getPostTitle(postId);
		String nickname = getUserNickname(userId);
		Notification notification = new Notification(userId, postId, postTitle, nickname + "님이 댓글을 달았습니다.");

		return notificationJpaRepository.save(notification);
	}

	@Override
	public void readNotification(NotificationReadMessage message) {
		Long userId = message.userId();
		Long notificationId = message.notificationId();

		Notification notification = notificationJpaRepository.findById(notificationId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_NOTIFICATION));
		notification.readNotification(userId);
	}

	@Override
	public void readAllNotifications(User user) {
		Long userId = user.getId();
		notificationJpaRepository.findAllByUserIdAndIsRead(userId, false)
			.forEach(notification -> notification.readNotification(userId));
	}

	@Override
	public List<Notification> findAllUnreadNotification(User user) {
		Long userId = user.getId();

		return notificationJpaRepository.findAllByUserIdAndIsRead(userId, false);
	}

	private String getPostTitle(Long postId) {
		return postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST))
			.getPostContent()
			.getTitle();
	}

	private String getUserNickname(Long userId) {
		return accountJpaRepository.findById(userId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_USER))
			.getNickname();
	}
}
