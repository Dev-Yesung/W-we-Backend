package wave.domain.post.dto;

import wave.domain.post.dto.request.CommentAddRequest;
import wave.domain.account.domain.entity.User;

public record CommentAddDto(
	long postId,
	Long userId,
	String email,
	String nickname,
	String comment
) {
	public static CommentAddDto of(
		long postId,
		CommentAddRequest addRequest,
		User user
	) {
		Long userId = user.getId();
		String email = user.getEmail();
		String nickname = user.getNickname();
		String comment = addRequest.comment();

		return new CommentAddDto(postId, userId, email, nickname, comment);
	}
}
