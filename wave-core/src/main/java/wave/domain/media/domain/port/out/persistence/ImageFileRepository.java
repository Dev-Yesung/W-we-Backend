package wave.domain.media.domain.port.out.persistence;

import wave.domain.media.domain.entity.ImageFile;
import wave.domain.media.domain.vo.Image;

public interface ImageFileRepository {

	Image findFileByPath(String uri);

	ImageFile saveFile(ImageFile imageFile);

	void deleteFileByPath(String uri);

}
