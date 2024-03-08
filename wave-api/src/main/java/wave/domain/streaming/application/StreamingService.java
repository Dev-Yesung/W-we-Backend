package wave.domain.streaming.application;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.port.out.PublishStreamingEventPort;
import wave.domain.media.domain.port.out.UpdateStreamingPort;
import wave.domain.media.domain.vo.Music;
import wave.domain.media.dto.StreamingSessionInfo;
import wave.domain.media.dto.request.LoadMusicRequest;
import wave.domain.media.dto.request.StreamingRecord;
import wave.domain.media.dto.request.StreamingSessionRequest;
import wave.domain.media.dto.response.LoadMusicResponse;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class StreamingService {

	private final UpdateStreamingPort updateStreamingPort;
	private final PublishStreamingEventPort publishStreamingEventPort;

	public LoadMusicResponse loadMusicFile(LoadMusicRequest request) {
		Music music = publishStreamingEventPort.publishLoadMusicFileEvent(request);
		String rangeHeader = request.rangeHeader();
		StreamingResponseBody streamingBody = music.createStreamingResponseBody(rangeHeader);
		String mimeType = music.getMimeType();
		long fileSize = music.getFileSize();
		long[] fileRange = music.extractFileRange(rangeHeader);

		updateStreamingPort.cacheStreamingStartValue(request.postId(), request.user(), request.ipAddress());

		return new LoadMusicResponse(streamingBody, mimeType, fileSize, fileRange[0], fileRange[1]);
	}

	public void recordStreamingSession(StreamingSessionRequest request) {
		if (!isValidClientTime(request.streamingRecord())) {
			return;
		}

		String ipAddress = request.ipAddress();
		Optional<String> optionalValue = updateStreamingPort.loadStreamingCacheValue(ipAddress);
		if (optionalValue.isEmpty()) {
			return;
		}

		String cacheValue = optionalValue.get();
		String[] tokenValues = cacheValue.split(":");
		Long postId = request.postId();
		if (!tokenValues[0].equals(String.valueOf(postId))
			|| !tokenValues[1].equals(request.user().getEmail())) {
			return;
		}

		long startMilliSec = Long.parseLong(tokenValues[2]);
		long endMilliSec = request.endMilliSec();
		long totalSec = (endMilliSec - startMilliSec) / 1_000L;
		if (totalSec < 30) {
			return;
		}

		Long userId = request.user().getId();
		StreamingSessionInfo sessionInfo
			= new StreamingSessionInfo(userId, postId, startMilliSec, endMilliSec, ipAddress);
		publishStreamingEventPort.publishRecordSessionEvent(sessionInfo);
	}

	private boolean isValidClientTime(StreamingRecord request) {
		Long endMilliSec = Long.valueOf(request.endTime());
		Long startMilliSec = Long.valueOf(request.startTime());
		long totalSec = (endMilliSec - startMilliSec) / 1_000L;

		return totalSec >= 30L;
	}

}
