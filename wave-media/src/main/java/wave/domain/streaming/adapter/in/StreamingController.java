package wave.domain.streaming.adapter.in;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.RequiredArgsConstructor;
import wave.domain.media.dto.request.LoadMusicRequest;
import wave.domain.media.dto.response.LoadMusicResponse;
import wave.domain.streaming.application.StreamingService;
import wave.global.common.WebAdapter;

@RequiredArgsConstructor
@RequestMapping("/api/streaming")
@RestController
@WebAdapter
public class StreamingController {

	private final StreamingService streamingService;

	@GetMapping("/{userId}/{postId}")
	public ResponseEntity<StreamingResponseBody> loadMusicByUserIdAndPostId(
		@PathVariable Long userId,
		@PathVariable Long postId,
		@RequestHeader(value = "Range", required = false) String rangeHeader
	) {
		LoadMusicRequest request = new LoadMusicRequest(userId, postId, rangeHeader);
		LoadMusicResponse response = streamingService.loadMusicFile(request);

		return getStreamingResponseEntity(response);
	}

	private ResponseEntity<StreamingResponseBody> getStreamingResponseEntity(
		LoadMusicResponse response
	) {
		HttpHeaders responseHeaders = new HttpHeaders();
		String mimeType = response.mimeType();
		long fileSize = response.fileSize();
		long startRange = response.startRange();
		long endRange = response.endRange();
		StreamingResponseBody streamingResponseBody = response.streamingResponseBody();

		String contentLength = String.valueOf(endRange - startRange + 1);
		String contentRange = String.format("bytes %d-%d/%d", startRange, endRange, fileSize);

		responseHeaders.add(CONTENT_TYPE, mimeType);
		responseHeaders.add(CONTENT_LENGTH, contentLength);
		responseHeaders.add(ACCEPT_RANGES, "bytes");
		responseHeaders.add(CONTENT_RANGE, contentRange);

		return new ResponseEntity<>(streamingResponseBody, responseHeaders, PARTIAL_CONTENT);
	}
}
