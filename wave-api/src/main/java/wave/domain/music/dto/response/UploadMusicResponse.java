package wave.domain.music.dto.response;

public record UploadMusicResponse(
	String fileName,
	String extension,
	String path
) {
}
