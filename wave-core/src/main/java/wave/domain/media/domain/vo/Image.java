package wave.domain.media.domain.vo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.FileException;
import wave.global.utils.FileUtils;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image {

	private String fileName;
	private String fileExtension;
	private String mimeType;
	private long fileSize;
	private String path;
	private byte[] fileData;

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

	public Path createFileDataByTemporary() {
		String uuid = UUID.randomUUID().toString();
		try {
			Path filePath = Files.createTempFile(uuid, ".tmp");

			return Files.write(filePath, fileData);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}

	public Path createFilePath() {
		FileUtils.createDirectoryIfNotExist(path);
		String fileNameWithExtension = FileUtils.appendFileNameWithExtension(fileName, fileExtension);

		return Paths.get(path + "/" + fileNameWithExtension);
	}

}
