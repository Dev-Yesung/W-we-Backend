package wave.domain.notification.adapter.out;

import java.util.List;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.comment.domain.entity.Comment;
import wave.domain.like.domain.entity.Like;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.notification.adapter.out.persistence.NotificationJpaRepository;
import wave.domain.notification.adapter.out.persistence.PostJpaRepository;
import wave.domain.notification.domain.entity.Notification;
import wave.domain.notification.domain.port.out.LoadNotificationPort;
import wave.domain.notification.domain.port.out.UpdateNotificationPort;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.vo.PostContent;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class NotificationPersistenceAdapter implements UpdateNotificationPort, LoadNotificationPort {

	private final NotificationJpaRepository notificationJpaRepository;
	private final PostJpaRepository postJpaRepository;

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
	public Notification saveNewPostMessage(Post post) {
		Notification notification = getNotification(post, "포스트 등록을 완료했습니다.");

		return notificationJpaRepository.save(notification);
	}

	@Override
	public Notification saveDeletePostMessage(Post post) {
		Notification notification = getNotification(post, "포스트 삭제를 완료했습니다.");

		return notificationJpaRepository.save(notification);
	}

	@Override
	public Notification saveNewLikeMessage(Like like) {
		Long userId = like.getUser().getId();
		Long postId = like.getPostId();
		String postTitle = getPostTitle(postId);
		String nickname = like.getUser().getNickname();
		Notification notification = new Notification(userId, postId, postTitle, nickname + "님이 이 음악을 추천합니다.");

		return notificationJpaRepository.save(notification);
	}

	@Override
	public Notification saveNewCommentMessage(Comment comment) {
		Long userId = comment.getUser().getId();
		Long postId = comment.getPostId();
		String postTitle = getPostTitle(postId);
		String nickname = comment.getUser().getNickname();
		Notification notification = new Notification(userId, postId, postTitle, nickname + "님이 댓글을 달았습니다.");

		return notificationJpaRepository.save(notification);
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

	private Notification getNotification(Post post, String message) {
		Long userId = post.getUser().getId();
		Long postId = post.getId();
		PostContent postContent = post.getPostContent();
		String postTitle = postContent.getTitle();

		return new Notification(userId, postId, postTitle, message);
	}
}
