package wave.global.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.vo.FileId;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;
import wave.global.error.exception.FileException;

public final class FileUtils {

	private static final String DIRECTORY_PATH_REGEX = "^(?:[a-zA-Z]:)?[\\\\/]?([a-zA-Z0-9]+[\\\\/]?)*$";

	public static void isValidFileExtension(String target, List<String> validExtensions) {
		validExtensions.stream()
			.filter(target::equals)
			.findAny()
			.orElseThrow(() -> new FileException(ErrorCode.INVALID_FILE_EXTENSION));
	}

	public static String convertWhiteSpaceToDash(String fileName) {
		return fileName.trim()
			.replaceAll(" ", "-");
	}

	public static String getNormalizedPath(String path) {
		String trimmedPath = path.trim();
		validatePath(path);

		return trimmedPath;
	}

	public static void createDirectoryIfNotExist(String directory) {
		File directoryFile = new File(directory);
		if (isAlreadyExistDirectory(directoryFile)) {
			return;
		}
		if (!directoryFile.mkdirs()) {
			throw new FileException(ErrorCode.UNABLE_TO_MAKE_DIRECTORY);
		}
	}

	public static String getFileExtension(Path filePath) {
		String fileName = filePath.getFileName().toString();
		int lastDotIndex = fileName.lastIndexOf('.');

		String extension = "";
		if (lastDotIndex > 0) {
			extension = fileName.substring(lastDotIndex + 1);
		}

		return extension;
	}

	public static String getFileNameWithoutExtension(Path filePath) {
		String fileName = filePath.getFileName().toString();
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex > 0) {
			fileName = fileName.substring(0, lastDotIndex);
		}

		return fileName;
	}

	public static String getMimeType(Path path) {
		try {
			return Files.probeContentType(path);
		} catch (IOException e) {
			throw new EntityException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}

	public static long getFileSize(Path filePath) {
		try {
			return Files.size(filePath);
		} catch (IOException e) {
			throw new EntityException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}

	public static void isPathExist(Path path) {
		if (!Files.exists(path)) {
			throw new EntityException(ErrorCode.NOT_FOUND_FILE);
		}
	}

	public static byte[] getFileData(Path filePath) {
		try {
			return Files.readAllBytes(filePath);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}

	private static void validatePath(String path) {
		Pattern pattern = Pattern.compile(DIRECTORY_PATH_REGEX);
		Matcher matcher = pattern.matcher(path);
		if (!matcher.matches()) {
			throw new FileException(ErrorCode.INVALID_FILE_PATH);
		}
	}

	private static boolean isAlreadyExistDirectory(File directory) {
		return directory.exists();
	}

	// private String getFileNameWithExtension(MusicFile musicFile) {
	// 	FileId fileId = musicFile.getFileId();
	// 	String musicFileName = musicFile.getMusicFileName();
	// 	String musicFileExtension = musicFile.getMusicFileExtension();
	//
	// 	String normalizedFileName = normalizeFileName(fileId, musicFileName);
	// 	isValidFileExtension(musicFileExtension);
	//
	// 	return normalizedFileName + "." + musicFileExtension;
	// }
	//
	// private String normalizeFileName(FileId fileId, String musicFileName) {
	// 	Long userId = fileId.getUserId();
	// 	Long postId = fileId.getPostId();
	// 	String fileNameSeparator = musicConfig.getFileNameSeparator();
	// 	String convertedFileName = convertWhiteSpaceToDash(musicFileName);
	//
	// 	return userId + fileNameSeparator + postId + fileNameSeparator + convertedFileName;
	// }

}
