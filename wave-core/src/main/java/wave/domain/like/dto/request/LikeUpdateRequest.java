package wave.domain.like.dto.request;

import wave.domain.account.domain.entity.User;

public record LikeUpdateRequest(
	long postId,
	User user
) {
}
