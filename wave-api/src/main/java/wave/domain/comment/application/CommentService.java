package wave.domain.comment.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import wave.domain.comment.domain.entity.Comment;
import wave.domain.comment.domain.port.out.LoadCommentPort;
import wave.domain.comment.domain.port.out.PublishCommentEventPort;
import wave.domain.comment.domain.port.out.UpdateCommentPort;
import wave.domain.comment.dto.CommentAddDto;
import wave.domain.comment.dto.CommentDeleteDto;
import wave.domain.comment.dto.response.CommentAddResponse;
import wave.domain.comment.dto.response.CommentDeleteResponse;
import wave.domain.comment.dto.response.CommentsSliceResponse;
import wave.domain.post.dto.response.PostDeleteResponse;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class CommentService {

	private final UpdateCommentPort updateCommentPort;
	private final LoadCommentPort loadCommentPort;
	private final PublishCommentEventPort publishCommentEventPort;

	public CommentAddResponse addComment(CommentAddDto commentAddDto) {
		Comment newComment = updateCommentPort.addComment(commentAddDto);
		publishCommentEventPort.publishCommentAddEvent(newComment);

		return CommentAddResponse.of(newComment);
	}

	@Transactional(readOnly = true)
	public CommentsSliceResponse getAllCommentsByCreatedDateAndOrderByDesc(long postId, Pageable pageable) {
		Slice<Comment> comments = loadCommentPort.getAllComments(postId, pageable);

		return CommentsSliceResponse.of(comments);
	}

	public CommentDeleteResponse deleteComment(CommentDeleteDto request) {
		Comment comment = updateCommentPort.deleteComment(request);

		return CommentDeleteResponse.of(comment);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void deleteAllComment(PostDeleteResponse message) {
		updateCommentPort.deleteAllByPostId(message);
	}

}
