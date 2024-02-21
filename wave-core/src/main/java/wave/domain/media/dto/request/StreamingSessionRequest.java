package wave.domain.media.dto.request;

import wave.domain.account.domain.entity.User;
import wave.domain.media.dto.request.StreamingRecord;

public record StreamingSessionRequest(
	StreamingRecord streamingRecord,
	Long postId,
	User user,
	String ipAddress,
	long endMilliSec
) {
}
