package wave.domain.media.domain.port.out;

import wave.domain.media.domain.entity.ImageFile;
import wave.domain.media.domain.vo.Image;

public interface ImageFileRepository {

	Image findFileByPath(String uri);

	void saveFile(ImageFile imageFile);

	void deleteFileByPath(String uri);

}
