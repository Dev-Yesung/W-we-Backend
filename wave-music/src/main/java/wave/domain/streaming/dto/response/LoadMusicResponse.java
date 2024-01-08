package wave.domain.streaming.dto.response;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public record LoadMusicResponse(
	StreamingResponseBody streamingResponseBody,
	String musicFilePath,
	long startRange,
	long endRange
) {

}
