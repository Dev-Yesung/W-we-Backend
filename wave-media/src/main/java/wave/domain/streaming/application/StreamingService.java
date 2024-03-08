package wave.domain.streaming.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.port.out.LoadMediaPort;
import wave.domain.media.domain.port.out.UpdateMediaPort;
import wave.domain.media.domain.vo.Music;
import wave.domain.media.dto.StreamingSessionInfo;
import wave.domain.media.dto.request.LoadMusicRequest;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class StreamingService {

	private final LoadMediaPort loadMediaPort;
	private final UpdateMediaPort updateMediaPort;

	@SendTo
	@KafkaListener(topics = "load_music_requests",
		groupId = "group_load_music_requests",
		containerFactory = "synchronousKafkaListenerContainerFactory")
	public Music loadMusicFile(LoadMusicRequest request) {
		MusicFile musicFile = loadMediaPort.loadMusicFile(request);

		return musicFile.getMusic();
	}

	@KafkaListener(topics = "save_streaming_session",
		groupId = "group_save_streaming_session",
		containerFactory = "kafkaListenerContainerFactory")
	public void recordStreamingSession(StreamingSessionInfo request) {
		updateMediaPort.saveStreamingSession(request);
	}

}
