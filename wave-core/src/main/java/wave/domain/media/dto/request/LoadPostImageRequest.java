package wave.domain.media.dto.request;

import wave.domain.account.domain.entity.User;

public record LoadPostImageRequest(
	Long postId,
	User user
) {
}
