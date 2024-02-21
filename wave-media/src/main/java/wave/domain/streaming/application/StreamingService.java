package wave.domain.streaming.application;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.port.out.LoadMediaPort;
import wave.domain.media.domain.port.out.UpdateMediaPort;
import wave.domain.media.dto.StreamingSessionInfo;
import wave.domain.media.dto.request.StreamingSessionRequest;
import wave.domain.media.dto.request.LoadMusicRequest;
import wave.domain.media.dto.request.StreamingRecord;
import wave.domain.media.dto.response.LoadMusicResponse;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class StreamingService {

	private final LoadMediaPort loadMediaPort;
	private final UpdateMediaPort updateMediaPort;

	public LoadMusicResponse loadMusicFile(LoadMusicRequest request) {
		MusicFile musicFile = loadMediaPort.loadMusicFile(request);
		String rangeHeader = request.rangeHeader();

		StreamingResponseBody streamingBody = musicFile.createStreamingResponseBody(rangeHeader);
		LoadMusicResponse loadMusicResponse
			= getLoadMusicResponse(musicFile, rangeHeader, streamingBody);
		updateMediaPort.cacheStreamingStartValue(request.postId(), request.user(), request.ipAddress());

		return loadMusicResponse;
	}

	// 3. 프론트엔드에서 음원 스트리밍 시간 측정을 한다.
	// 4. 프론트엔드에서 음원이 멈출 때 마다, 시간을 일시적으로 기록하고
	// 총 시간이 30초를 넘으면 서버에 전송하고 스트리밍 시간 초기화.
	public void recordStreamingSession(StreamingSessionRequest request) {
		// todo: 깔끔하게 정리하기
		if (!isValidClientTime(request.streamingRecord())) {
			return;
		}

		String ipAddress = request.ipAddress();
		Optional<String> optionalValue = loadMediaPort.loadStreamingCacheValue(ipAddress);
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
			= new StreamingSessionInfo(postId, userId, startMilliSec, endMilliSec, ipAddress);
		updateMediaPort.saveStreamingSession(sessionInfo);
	}

	private LoadMusicResponse getLoadMusicResponse(
		MusicFile musicFile,
		String rangeHeader,
		StreamingResponseBody streamingBody
	) {
		String mimeType = musicFile.getMusicMimeType();
		long fileSize = musicFile.getMusicFileSize();
		long[] fileRange = musicFile.extractFileRange(rangeHeader);

		return new LoadMusicResponse(streamingBody, mimeType, fileSize, fileRange[0], fileRange[1]);
	}

	private boolean isValidClientTime(StreamingRecord request) {
		Long endMilliSec = Long.valueOf(request.endTime());
		Long startMilliSec = Long.valueOf(request.startTime());
		long totalSec = (endMilliSec - startMilliSec) / 1_000L;

		return totalSec >= 30L;
	}

}
