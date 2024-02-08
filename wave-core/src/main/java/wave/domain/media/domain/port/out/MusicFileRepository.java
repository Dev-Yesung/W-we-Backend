package wave.domain.media.domain.port.out;

import org.springframework.web.multipart.MultipartFile;

import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.vo.Music;

public interface MusicFileRepository {

	Music findFileByPath(String path);

	void saveFile(MusicFile musicFile, MultipartFile file);

	void deleteFileByPath(String path);

}
