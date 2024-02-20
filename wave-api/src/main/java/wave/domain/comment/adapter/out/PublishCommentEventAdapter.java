package wave.domain.comment.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.comment.domain.entity.Comment;
import wave.domain.comment.domain.port.out.PublishCommentEventPort;
import wave.domain.comment.domain.port.out.broker.CommentEventBroker;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class PublishCommentEventAdapter implements PublishCommentEventPort {

	private final CommentEventBroker commentEventBroker;

	@Override
	public void publishCommentAddEvent(Comment comment) {
		commentEventBroker.publishMessage("comment_add_message", comment);
	}

}
