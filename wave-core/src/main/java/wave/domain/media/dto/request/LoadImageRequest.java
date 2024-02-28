package wave.domain.media.dto.request;

import wave.domain.account.domain.entity.User;

public record LoadImageRequest(
	Long postId,
	User user
) {
}
