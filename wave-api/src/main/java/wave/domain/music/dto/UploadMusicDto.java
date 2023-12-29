package wave.domain.music.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadMusicDto(
	Long userId,
	Long postId,
	MultipartFile ownMusicFile
) {
}
