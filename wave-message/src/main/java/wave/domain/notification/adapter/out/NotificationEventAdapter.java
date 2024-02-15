package wave.domain.notification.adapter.out;

import org.springframework.data.redis.core.RedisOperations;

import lombok.RequiredArgsConstructor;
import wave.domain.notification.adapter.out.persistence.PostJpaRepository;
import wave.domain.notification.dto.NotificationMessage;
import wave.domain.notification.entity.Notification;
import wave.domain.notification.port.out.PublishNotificationEventPort;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.vo.PostContent;
import wave.global.common.EventAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@EventAdapter
public class NotificationEventAdapter implements PublishNotificationEventPort {

	private final PostJpaRepository postJpaRepository;
	private final RedisOperations<String, NotificationMessage> notificationOperations;

	@Override
	public void publishUploadMessageToClient(Notification notification) {
		Long postId = notification.getPostId();
		Post post = postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));
		NotificationMessage notificationMessage = NotificationMessage.of(notification, post);
		String channelName = notification.getChannelName();

		notificationOperations.convertAndSend(channelName, notificationMessage);
	}

}
