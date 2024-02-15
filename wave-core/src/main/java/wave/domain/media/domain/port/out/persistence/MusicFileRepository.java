package wave.domain.media.domain.port.out.persistence;

import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.vo.Music;

public interface MusicFileRepository {

	Music findFileByPath(String path);

	MusicFile saveFile(MusicFile musicFile);

	void deleteFileByPath(String path);

}
