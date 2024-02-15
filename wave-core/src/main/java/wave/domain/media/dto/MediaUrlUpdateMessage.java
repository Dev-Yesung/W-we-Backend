package wave.domain.media.dto;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUrl;

public record MediaUrlUpdateMessage(
	FileId fileId,
	MediaUrl mediaUrl
) {
}
