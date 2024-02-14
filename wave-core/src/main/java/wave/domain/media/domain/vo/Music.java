package wave.domain.media.domain.vo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;
import wave.global.error.exception.FileException;
import wave.global.utils.FileUtils;

@Slf4j
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Music {

	private String fileName;
	private String fileExtension;
	private String mimeType;
	private long fileSize;
	private String path;
	private byte[] fileData;

	public static Music toMusic(Music before, String path) {
		String fileName = before.getFileName();
		String fileExtension = before.getFileExtension();
		String mimeType = before.getMimeType();
		long fileSize = before.getFileSize();
		byte[] fileData = before.getFileData();

		return new Music(fileName, fileExtension, mimeType, fileSize, path, fileData);
	}

	public static Music toMusic(Path realPath, String uri) {
		String fileName = FileUtils.getFileNameWithoutExtension(realPath);
		String extension = FileUtils.getFileExtension(realPath);
		String mimeType = FileUtils.getMimeType(realPath);
		long fileSize = FileUtils.getFileSize(realPath);
		byte[] fileData = FileUtils.getFileData(realPath);

		return new Music(fileName, extension, mimeType, fileSize, uri, fileData);
	}

	public StreamingResponseBody createStreamingResponseBody(String rangeHeader) {
		long[] fileRange = extractFileRange(rangeHeader);
		long startRange = fileRange[0];
		long endRange = fileRange[1];

		return outputStream -> {
			try (RandomAccessFile file = new RandomAccessFile(path, "r")) {
				byte[] fileBuffer = new byte[1024];
				long currentPosition = startRange;

				file.seek(currentPosition);
				while (currentPosition < endRange) {
					file.read(fileBuffer);
					outputStream.write(fileBuffer);
					currentPosition += fileBuffer.length;
				}

				outputStream.flush();
			} catch (Exception e) {
				throw new BusinessException(ErrorCode.UNABLE_TO_OUTPUT);
			}
		};
	}

	public long[] extractFileRange(String rangeHeader) {
		if (!isValidRangeFormat(rangeHeader)) {
			return getEntireRange();
		}

		log.info("Read range seeking value -> Range Values[{}]", rangeHeader);
		String[] ranges = rangeHeader.split("-");
		long startRange = getStartRange(ranges[0]);
		long endRange = getEndRange(ranges[1]);

		log.info("Parsed Range Values: {} - {}", startRange, endRange);
		return new long[] {startRange, endRange};
	}

	public Path createFilePath() {
		FileUtils.createDirectoryIfNotExist(path);
		String fileNameWithExtension = FileUtils.appendFileNameWithExtension(fileName, fileExtension);

		return Paths.get(path + "/" + fileNameWithExtension);
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

	private boolean isValidRangeFormat(String rangeHeader) {
		int dashIndex = rangeHeader.indexOf("-");
		int endOfIndex = rangeHeader.length() - 1;

		return StringUtils.hasText(rangeHeader)
			   && dashIndex > 0
			   && dashIndex <= endOfIndex;
	}

	private long[] getEntireRange() {
		return new long[] {0, fileSize - 1L};
	}

	private long getStartRange(String rangeValue) {
		if (!StringUtils.hasText(rangeValue)) {
			return 0;
		}

		log.info("startRange value is {}", rangeValue);
		return parseRangeToNumeric(rangeValue, 0L);
	}

	private long getEndRange(String rangeValue) {
		long fileEndRange = fileSize - 1L;
		long endRange = parseRangeToNumeric(rangeValue, fileEndRange);

		if (fileSize < endRange) {
			log.info("end range value is {}", endRange);
			return fileEndRange;
		}

		log.info("end range value is {}", endRange);
		return endRange;
	}

	private long parseRangeToNumeric(String range, Long defaultRange) {
		try {
			long parsedValue = Long.parseLong(range);

			log.info("Parsed range to long: {}", parsedValue);
			return parsedValue;
		} catch (NumberFormatException e) {
			log.info("Invalid Value: {}. So, return DEFAULT_START_RANGE", range);
			return defaultRange;
		}
	}
}
