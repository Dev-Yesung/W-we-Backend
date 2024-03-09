package wave.domain.media.domain.port.out;

import wave.domain.media.domain.entity.ImageFile;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.dto.request.LoadPostImageRequest;
import wave.domain.media.dto.request.LoadMusicRequest;

public interface LoadMediaPort {

	MusicFile loadMusicFile(LoadMusicRequest request);

	ImageFile loadImageFile(LoadPostImageRequest request);

}
