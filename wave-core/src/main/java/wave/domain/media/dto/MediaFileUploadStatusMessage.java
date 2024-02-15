package wave.domain.media.dto;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaUploadStatus;

public record MediaFileUploadStatusMessage(
	FileId fileId,
	MediaUploadStatus uploadStatus
) {
}
