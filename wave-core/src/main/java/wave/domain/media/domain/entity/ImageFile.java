package wave.domain.media.domain.entity;

import lombok.Getter;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Image;

@Getter
public class ImageFile extends File {
	private final Image image;

	public ImageFile(FileId fileId, Image image) {
		super(fileId);
		this.image = image;
	}
}
