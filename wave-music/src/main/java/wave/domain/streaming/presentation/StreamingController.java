package wave.domain.streaming.presentation;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.RequiredArgsConstructor;
import wave.domain.streaming.application.StreamingService;
import wave.domain.streaming.dto.request.LoadMusicRequest;
import wave.domain.streaming.dto.response.LoadMusicResponse;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@RequiredArgsConstructor
@RequestMapping("/api/music")
@RestController
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
		String mimeType = getMimeType(response);
		long startRange = response.startRange();
		long endRange = response.endRange();
		long fileSize = getFileSize(response);
		String contentLength = String.valueOf(endRange - startRange + 1);
		String contentRange = String.format("bytes %d-%d/%d", startRange, endRange, fileSize);

		responseHeaders.add(CONTENT_TYPE, mimeType);
		responseHeaders.add(CONTENT_LENGTH, contentLength);
		responseHeaders.add(ACCEPT_RANGES, "bytes");
		responseHeaders.add(CONTENT_RANGE, contentRange);
		StreamingResponseBody streamingResponseBody = response.streamingResponseBody();

		return new ResponseEntity<>(streamingResponseBody, responseHeaders, PARTIAL_CONTENT);
	}

	private long getFileSize(LoadMusicResponse response) {
		String filePath = response.musicFilePath();
		Path path = Paths.get(filePath);
		try {
			return Files.size(path);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.UNABLE_TO_GET_FILE_INFO);
		}
	}

	private String getMimeType(
		LoadMusicResponse response) {
		String filePath = response.musicFilePath();
		Path path = Path.of(filePath);
		try {
			return Files.probeContentType(path);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.UNABLE_TO_GET_FILE_INFO);
		}
	}
}
