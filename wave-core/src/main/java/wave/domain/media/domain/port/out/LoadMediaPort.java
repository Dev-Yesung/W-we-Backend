package wave.domain.media.domain.port.out;

import java.util.Optional;

import wave.domain.media.domain.entity.ImageFile;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.dto.request.LoadMusicRequest;

public interface LoadMediaPort {

	MusicFile loadMusicFile(LoadMusicRequest request);

	ImageFile loadImageFile();

	Optional<String> loadStreamingCacheValue(String ipAddress);
}
