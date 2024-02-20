package wave.domain.comment.dto.response;

import java.util.List;

import org.springframework.data.domain.Slice;

import wave.domain.comment.domain.entity.Comment;

public record CommentsSliceResponse(
	List<CommentResponse> comments,
	boolean hasNext
) {

	public static CommentsSliceResponse of(Slice<Comment> comments) {
		List<CommentResponse>  commentResponses = comments.stream()
			.map(CommentResponse::of)
			.toList();
		boolean hasNext = comments.hasNext();

		return new CommentsSliceResponse(commentResponses, hasNext);
	}
}
