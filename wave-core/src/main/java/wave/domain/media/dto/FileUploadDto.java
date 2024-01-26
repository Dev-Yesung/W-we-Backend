package wave.domain.media.dto;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadDto(
	Long userId,
	Long postId,
	MultipartFile file
) {
}
