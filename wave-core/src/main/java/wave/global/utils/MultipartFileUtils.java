package wave.global.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import wave.global.error.ErrorCode;
import wave.global.error.exception.FileException;

public final class MultipartFileUtils {

	public static byte[] getBytes(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
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

	public static void isPermittedFileSize(MultipartFile file, long maxFileSize) {
		long fileSize = getFileSize(file);
		if (fileSize > maxFileSize) {
			throw new FileException(ErrorCode.INVALID_FILE_SIZE);
		}
	}


	private static String getOriginalFileName(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (fileName == null || fileName.isEmpty()) {
			throw new FileException(ErrorCode.INVALID_FILE_NAME);
		}

		return fileName;
	}

	public static InputStream getInputStream(MultipartFile multipartFile) {
		try {
			return multipartFile.getInputStream();
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}
}
