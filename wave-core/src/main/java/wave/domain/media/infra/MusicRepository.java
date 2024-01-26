package wave.domain.media.infra;

import org.springframework.web.multipart.MultipartFile;

import wave.domain.media.domain.entity.MusicFile;

public interface MusicRepository {

	void saveFile(MusicFile musicFile, MultipartFile file);

	void deleteFile(String path);

}
