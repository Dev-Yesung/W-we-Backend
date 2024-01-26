package wave.global.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import wave.global.error.ErrorCode;
import wave.global.error.exception.FileException;

public final class FileUtils {

	private static final String DIRECTORY_PATH_REGEX = "^(?:[a-zA-Z]:)?[\\\\/]?([a-zA-Z0-9]+[\\\\/]?)*$";

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

	public static String getPureFileName(MultipartFile file) {
		String originalFileName = getOriginalFileName(file);
		String[] splitStrings = originalFileName.split("\\.");
		int length = splitStrings.length;
		if (length == 0) {
			throw new FileException(ErrorCode.INVALID_FILE_EXTENSION);
		}

		return splitStrings[0];
	}

	public static String getFileExtension(MultipartFile file) {
		String originalFileName = getOriginalFileName(file);
		String[] splitStrings = originalFileName.split("\\.");
		int length = splitStrings.length;
		if (length == 0) {
			throw new FileException(ErrorCode.INVALID_FILE_EXTENSION);
		}

		return splitStrings[length - 1];
	}

	public static long getFileSize(MultipartFile file) {
		long fileSize = file.getSize();
		if (fileSize > 0) {
			throw new FileException(ErrorCode.INVALID_FILE_SIZE);
		}

		return fileSize;
	}

	private static void validatePath(String path) {
		Pattern pattern = Pattern.compile(DIRECTORY_PATH_REGEX);
		Matcher matcher = pattern.matcher(path);
		if (!matcher.matches()) {
			throw new FileException(ErrorCode.INVALID_PATH);
		}
	}

	private static String getOriginalFileName(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (fileName == null || fileName.isEmpty()) {
			throw new FileException(ErrorCode.INVALID_FILE_NAME);
		}

		return fileName;
	}

	private static boolean isAlreadyExistDirectory(File directory) {
		return directory.exists();
	}
}
