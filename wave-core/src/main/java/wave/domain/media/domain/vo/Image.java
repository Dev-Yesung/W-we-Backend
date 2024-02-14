package wave.domain.media.domain.vo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.FileException;
import wave.global.utils.FileUtils;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class Image {

	private final String fileName;
	private final String fileExtension;
	private final String mimeType;
	private final long fileSize;
	private final String path;
	private final byte[] fileData;

	public static Image toImage(Image before, String path) {
		String fileName = before.getFileName();
		String fileExtension = before.getFileExtension();
		String mimeType = before.getMimeType();
		long fileSize = before.getFileSize();
		byte[] fileData = before.getFileData();

		return new Image(fileName, fileExtension, mimeType, fileSize, path, fileData);
	}

	public static Image toImage(Path realPath, String uri) {
		String fileName = FileUtils.getFileNameWithoutExtension(realPath);
		String extension = FileUtils.getFileExtension(realPath);
		String mimeType = FileUtils.getMimeType(realPath);
		long fileSize = FileUtils.getFileSize(realPath);
		byte[] fileData = FileUtils.getFileData(realPath);

		return new Image(fileName, extension, mimeType, fileSize, uri, fileData);
	}

	public Path createFileDataByPath() {
		Path filePath = Paths.get(path);
		try {
			return Files.write(filePath, fileData);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA);
		}
	}


}
