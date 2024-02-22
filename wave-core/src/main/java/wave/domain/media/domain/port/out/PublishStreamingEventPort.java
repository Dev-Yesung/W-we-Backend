package wave.domain.media.domain.port.out;

import wave.domain.media.domain.vo.Music;
import wave.domain.media.dto.StreamingSessionInfo;
import wave.domain.media.dto.request.LoadMusicRequest;

public interface PublishStreamingEventPort {

	Music publishLoadMusicFileEvent(LoadMusicRequest request);

	void publishRecordSessionEvent(StreamingSessionInfo request);

}
