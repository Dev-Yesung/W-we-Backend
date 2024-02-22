package wave.domain.media.dto.request;

import wave.domain.account.domain.entity.User;

public record LoadMusicRequest(
	Long postId,
	String rangeHeader,
	User user,
	String ipAddress
) {
}
