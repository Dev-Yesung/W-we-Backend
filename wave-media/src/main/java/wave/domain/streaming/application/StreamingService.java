package wave.domain.streaming.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.port.out.LoadMediaPort;
import wave.domain.media.dto.request.LoadMusicRequest;
import wave.domain.media.dto.response.LoadMusicResponse;
import wave.global.common.UseCase;

@Transactional
@RequiredArgsConstructor
@UseCase
public class StreamingService {

	private final LoadMediaPort loadMediaPort;

	@Transactional(readOnly = true)
	public LoadMusicResponse loadMusicFile(LoadMusicRequest request) {
		MusicFile musicFile = loadMediaPort.loadMusicFile(request);
		String rangeHeader = request.rangeHeader();

		StreamingResponseBody streamingBody = musicFile.createStreamingResponseBody(rangeHeader);
		String mimeType = musicFile.getMusicMimeType();
		long fileSize = musicFile.getMusicFileSize();
		long[] fileRange = musicFile.extractFileRange(rangeHeader);

		return new LoadMusicResponse(streamingBody, mimeType, fileSize, fileRange[0], fileRange[1]);
	}
}
