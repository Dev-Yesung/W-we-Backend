package wave.domain.upload.infra;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.config.MusicConfig;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.port.out.MusicFileRepository;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Music;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;
import wave.global.error.exception.FileException;
import wave.global.utils.FileUtils;

@RequiredArgsConstructor
@Repository
public class MusicFileSystemFileRepository implements MusicFileRepository {

	private final MusicConfig musicConfig;

	@Override
	public Music findFileByPath(String path) {
		Path filePath = Paths.get(path);
		isPathExist(filePath);

		String fileName = getFileNameWithoutExtension(filePath);
		String extension = getFileExtension(filePath);
		String mimeType = getMimeType(filePath);
		long fileSize = getFileSize(filePath);

		return new Music(fileName, extension, mimeType, fileSize, path);
	}

	@Override
	public void saveFile(MusicFile musicFile, MultipartFile file) {
		String path = musicFile.getPath();
		FileUtils.createDirectoryIfNotExist(path);
		String fileNameWithExtension = getFileNameWithExtension(musicFile);
		saveFileOnStorage(path, fileNameWithExtension, file);
	}

	@Override
	public void deleteFileByPath(String path) {
		deleteFileOnStorage(path);
	}

	private void isPathExist(Path path) {
		if (!Files.exists(path)) {
			throw new EntityException(ErrorCode.NOT_FOUND_FILE);
		}
	}

	private String getFileNameWithoutExtension(Path filePath) {
		String fileName = filePath.getFileName().toString();
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex > 0) {
			fileName = fileName.substring(0, lastDotIndex);
		}

		return fileName;
	}

	private String getFileExtension(Path filePath) {
		String fileName = filePath.getFileName().toString();
		int lastDotIndex = fileName.lastIndexOf('.');

		String extension = "";
		if (lastDotIndex > 0) {
			extension = fileName.substring(lastDotIndex + 1);
		}

		return extension;
	}

	private String getMimeType(Path path) {
		try {
			return Files.probeContentType(path);
		} catch (IOException e) {
			throw new EntityException(ErrorCode.UNABLE_TO_GET_FILE_INFO, e);
		}
	}

	private long getFileSize(Path filePath) {
		try {
			return Files.size(filePath);
		} catch (IOException e) {
			throw new EntityException(ErrorCode.UNABLE_TO_GET_FILE_INFO, e);
		}
	}

	private String getFileNameWithExtension(MusicFile musicFile) {
		FileId fileId = musicFile.getFileId();
		String musicFileName = musicFile.getMusicFileName();
		String musicFileExtension = musicFile.getMusicFileExtension();

		String normalizedFileName = normalizeFileName(fileId, musicFileName);
		validateFileExtension(musicFileExtension);

		return normalizedFileName + "." + musicFileExtension;
	}

	private void saveFileOnStorage(String path, String fileName, MultipartFile file) {
		validateFileSize(file);
		Path filePath = Paths.get(path + "/" + fileName);
		try {
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, filePath);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_UPLOAD_FILE, e);
		}
	}

	private void deleteFileOnStorage(String path) {
		Path directoryPath = Paths.get(path);
		try (Stream<Path> files = Files.list(directoryPath)) {
			files.forEach(file -> {
				String fileDirectory = path + "/" + file.getFileName().toString();
				Path filePath = Paths.get(fileDirectory);
				try {
					Files.delete(filePath);
				} catch (IOException e) {
					throw new FileException(ErrorCode.NOT_FOUND_FILE, e);
				}
			});
			Files.delete(directoryPath);
		} catch (IOException e) {
			throw new FileException(ErrorCode.NOT_FOUND_DIRECTORY, e);
		}
	}

	private String normalizeFileName(FileId fileId, String musicFileName) {
		Long userId = fileId.getUserId();
		Long postId = fileId.getPostId();
		String fileNameSeparator = musicConfig.getFileNameSeparator();
		String convertedFileName = convertWhiteSpaceToDash(musicFileName);

		return userId + fileNameSeparator + postId + fileNameSeparator + convertedFileName;
	}

	private void validateFileExtension(String fileExtension) {
		List<String> permittedFileExtensions = musicConfig.getPermittedFileExtensions();
		permittedFileExtensions.stream()
			.filter(fileExtension::equals)
			.findAny()
			.orElseThrow(() -> new FileException(ErrorCode.INVALID_FILE_EXTENSION));
	}

	private void validateFileSize(MultipartFile file) {
		long maxFileSize = musicConfig.getMaxFileSize();
		long fileSize = FileUtils.getFileSize(file);
		if (fileSize > maxFileSize) {
			throw new FileException(ErrorCode.INVALID_FILE_SIZE);
		}
	}

	private String convertWhiteSpaceToDash(String fileName) {
		return fileName.trim()
			.replaceAll(" ", "-");
	}

}
