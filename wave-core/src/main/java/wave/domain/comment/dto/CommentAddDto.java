package wave.domain.comment.dto;

import wave.domain.account.domain.entity.User;
import wave.domain.comment.dto.request.CommentAddRequest;

public record CommentAddDto(
	long postId,
	User user,
	String email,
	String nickname,
	String comment
) {
	public static CommentAddDto of(
		long postId,
		CommentAddRequest addRequest,
		User user
	) {
		String email = user.getEmail();
		String nickname = user.getNickname();
		String comment = addRequest.comment();

		return new CommentAddDto(postId, user, email, nickname, comment);
	}
}
