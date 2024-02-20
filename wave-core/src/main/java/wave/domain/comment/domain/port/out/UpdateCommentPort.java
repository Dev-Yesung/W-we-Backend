package wave.domain.comment.domain.port.out;

import wave.domain.comment.domain.entity.Comment;
import wave.domain.comment.dto.CommentAddDto;
import wave.domain.comment.dto.CommentDeleteDto;
import wave.domain.post.dto.response.PostDeleteResponse;

public interface UpdateCommentPort {

	Comment addComment(CommentAddDto commentAddDto);

	Comment deleteComment(CommentDeleteDto request);

	void deleteAllByPostId(PostDeleteResponse message);
}
