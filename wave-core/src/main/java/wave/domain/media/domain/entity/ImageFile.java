package wave.domain.media.domain.entity;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.domain.vo.MediaMultipartFile;

@Getter
public class ImageFile extends AbstractMediaFile {

	private final Image image;

	public ImageFile(FileId fileId, Image image) {
		super(fileId);
		this.image = image;
	}

	public MultipartFile convertByteDataToMultipartFile() {
		Path fileDataByPath = image.createFileDataByPath();

		return new MediaMultipartFile(fileDataByPath);
	}

	public Path createFileDataByPath() {
		return image.createFileDataByPath();
	}
}
