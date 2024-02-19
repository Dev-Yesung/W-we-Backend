package wave.domain.media.dto;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.domain.vo.Music;

public record MediaFileUploadMessage(
	FileId fileId,
	Image image,
	Music music
) {
}
