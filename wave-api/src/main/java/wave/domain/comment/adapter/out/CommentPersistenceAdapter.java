package wave.domain.comment.adapter.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.comment.adapter.out.persistence.CommentJpaRepository;
import wave.domain.comment.domain.entity.Comment;
import wave.domain.comment.domain.port.out.LoadCommentPort;
import wave.domain.comment.domain.port.out.UpdateCommentPort;
import wave.domain.comment.dto.CommentAddDto;
import wave.domain.comment.dto.CommentDeleteDto;
import wave.domain.post.adapter.out.persistence.PostJpaRepository;
import wave.domain.post.dto.response.PostDeleteResponse;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class CommentPersistenceAdapter implements UpdateCommentPort, LoadCommentPort {

	private final PostJpaRepository postJpaRepository;
	private final CommentJpaRepository commentJpaRepository;

	@Override
	public Comment addComment(final CommentAddDto commentAddDto) {
		final long postId = commentAddDto.postId();
		if (!postJpaRepository.existsById(postId)) {
			throw new EntityException(ErrorCode.NOT_FOUND_POST);
		}

		User user = commentAddDto.user();
		String comment = commentAddDto.comment();
		Comment newComment = new Comment(postId, user, comment);

		return commentJpaRepository.save(newComment);
	}

	@Override
	public Comment deleteComment(CommentDeleteDto request) {
		long commentId = request.commentId();
		Comment comment = commentJpaRepository.findById(commentId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_COMMENT));

		User user = request.user();
		comment.isSameWriter(user);
		commentJpaRepository.delete(comment);

		return comment;
	}

	@Override
	public void deleteAllByPostId(PostDeleteResponse message) {
		long postId = message.postId();
		commentJpaRepository.deleteAllByPostId(postId);
	}

	@Override
	public Slice<Comment> getAllComments(long postId, Pageable pageable) {
		return commentJpaRepository.findAllByPostIdOrderBy(postId, pageable);
	}
}
