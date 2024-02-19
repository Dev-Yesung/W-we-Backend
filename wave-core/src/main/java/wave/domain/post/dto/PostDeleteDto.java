package wave.domain.post.dto;

import wave.domain.account.domain.entity.User;

public record PostDeleteDto(
	Long postId,
	User user
) {
}
