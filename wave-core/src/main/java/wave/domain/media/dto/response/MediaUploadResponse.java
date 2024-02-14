package wave.domain.media.dto.response;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Media;

public record MediaUploadResponse(
	FileId fileId,
	Media media
) {
}
