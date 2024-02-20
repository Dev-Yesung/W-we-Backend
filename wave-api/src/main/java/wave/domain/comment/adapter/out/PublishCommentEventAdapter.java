package wave.domain.comment.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.comment.domain.entity.Comment;
import wave.domain.comment.domain.port.out.PublishCommentEventPort;
import wave.domain.comment.domain.port.out.broker.CommentEventBroker;
import wave.domain.notification.dto.CommonNotificationMessage;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class PublishCommentEventAdapter implements PublishCommentEventPort {

	private final CommentEventBroker commentEventBroker;

	@Override
	public void publishCommentAddEvent(Comment comment) {
		Long userId = comment.getUser().getId();
		Long postId = comment.getPostId();
		CommonNotificationMessage message = new CommonNotificationMessage(userId, postId);
		commentEventBroker.publishMessage("comment_add_message", message);
	}

}
