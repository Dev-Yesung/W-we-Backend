package wave.domain.streaming.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.port.out.PublishStreamingEventPort;
import wave.domain.media.domain.port.out.broker.StreamingEventBroker;
import wave.domain.media.domain.vo.Music;
import wave.domain.media.dto.StreamingSessionInfo;
import wave.domain.media.dto.request.LoadMusicRequest;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class StreamingEventAdapter implements PublishStreamingEventPort {

	private final StreamingEventBroker streamingEventBroker;

	@Override
	public Music publishLoadMusicFileEvent(LoadMusicRequest request) {
		return (Music)streamingEventBroker.publishAndReplyStreamingEvent(
			"load_music_requests", request, "load_music_replies");
	}

	@Override
	public void publishRecordSessionEvent(StreamingSessionInfo request) {
		streamingEventBroker.publishStreamingEvent("save_streaming_session", request);
	}

}
