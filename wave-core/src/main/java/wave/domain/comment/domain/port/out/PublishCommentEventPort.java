package wave.domain.comment.domain.port.out;

import wave.domain.comment.domain.entity.Comment;

public interface PublishCommentEventPort {

	void publishCommentAddEvent(Comment comment);

}
