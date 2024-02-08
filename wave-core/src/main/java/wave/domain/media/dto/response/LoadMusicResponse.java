package wave.domain.media.dto.response;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public record LoadMusicResponse(
	StreamingResponseBody streamingResponseBody,
	String mimeType,
	long fileSize,
	long startRange,
	long endRange
) {
}
