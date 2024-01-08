package wave.domain.post.dto.request;

import wave.domain.user.domain.User;

public record LikeUpdateRequest(
	long postId,
	User user
) {
}
