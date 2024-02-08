package wave.domain.media.domain.port.out;

import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.dto.request.LoadMusicRequest;

public interface LoadMusicPort {
	MusicFile loadMusicFile(LoadMusicRequest request);
}
