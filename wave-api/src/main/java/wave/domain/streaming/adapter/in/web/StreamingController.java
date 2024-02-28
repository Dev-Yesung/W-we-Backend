package wave.domain.streaming.adapter.in.web;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.media.dto.request.LoadMusicRequest;
import wave.domain.media.dto.request.StreamingRecord;
import wave.domain.media.dto.request.StreamingSessionRequest;
import wave.domain.media.dto.response.LoadMusicResponse;
import wave.domain.streaming.application.StreamingService;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;

@RequiredArgsConstructor
@RequestMapping("/api/streaming")
@RestController
@WebAdapter
public class StreamingController {

	private final StreamingService streamingService;

	@GetMapping("/music/posts/{postId}")
	public ResponseEntity<StreamingResponseBody> loadMusicByPostIdAndUserId(
		@PathVariable Long postId,
		@RequestHeader(value = "Range", required = false) String rangeHeader,
		HttpServletRequest httpServletRequest,
		@AuthenticationUser User user
	) {
		String ipAddress = httpServletRequest.getRemoteAddr();
		LoadMusicRequest request = new LoadMusicRequest(postId, rangeHeader, user, ipAddress);
		LoadMusicResponse response = streamingService.loadMusicFile(request);

		return getStreamingResponseEntity(response);
	}

	@PostMapping("/posts/{postId}/sessions")
	public ResponseEntity<Void> recordStreamingCount(
		@PathVariable Long postId,
		@RequestBody StreamingRecord request,
		HttpServletRequest httpServletRequest,
		@AuthenticationUser User user
	) {
		long endMilliSec = System.currentTimeMillis();
		String remoteAddress = httpServletRequest.getRemoteAddr();
		StreamingSessionRequest sessionInfo
			= new StreamingSessionRequest(request, postId, user, remoteAddress, endMilliSec);
		streamingService.recordStreamingSession(sessionInfo);

		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
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
