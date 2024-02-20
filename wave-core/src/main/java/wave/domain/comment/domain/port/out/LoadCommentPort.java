package wave.domain.comment.domain.port.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import wave.domain.comment.domain.entity.Comment;

public interface LoadCommentPort {
	Slice<Comment> getAllComments(long postId, Pageable pageable);
}
