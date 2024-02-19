package wave.domain.comment.dto;

import wave.domain.account.domain.entity.User;

public record CommentDeleteDto(
	long commentId,
	User user
) {
}
