package wave.domain.streaming.application;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wave.config.MusicConfig;
import wave.domain.streaming.dto.request.LoadMusicRequest;
import wave.domain.streaming.dto.response.LoadMusicResponse;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class StreamingService {
	private static final long DEFAULT_RANGE_VALUE = 0L;
	private final MusicConfig musicConfig;

	@Transactional(readOnly = true)
	public LoadMusicResponse loadMusicFile(LoadMusicRequest request) {
		String filePath = getFilePath(request);
		Path musicFilePath = Paths.get(filePath);
		validatePathExistence(musicFilePath);
		String rangeValues = request.rangeHeader();
		if (!StringUtils.hasText(rangeValues)) {
			log.info("RangeValues is Null. So read all music file descriptions.");
			return loadEntireMusicFile(filePath);
		}

		log.info("Read range seeking value -> Range Values[{}]", rangeValues);

		long startRange = DEFAULT_RANGE_VALUE;
		long endRange = getInitialEndRange(musicFilePath);
		int dashPosition = rangeValues.indexOf("-");
		if (dashPosition > 0 && dashPosition <= rangeValues.length() - 1) {
			String[] ranges = rangeValues.split("-");
			log.info("ArraySize: {}", ranges.length);
			if (StringUtils.hasText(ranges[0])) {
				log.info("Ranges[0] value is {}", ranges[0]);
				startRange = parseRangeToNumericValue(ranges[0]);
			}

			if (ranges.length > 1) {
				log.info("Ranges[1] value is {}", ranges[1]);
				long parseEndRange = parseRangeToNumericValue(ranges[1]);
				endRange = getLimitedEndRangeIfExceedFileSize(parseEndRange, musicFilePath);
			}
		}
		log.info("Parsed Range Values: {} - {}", startRange, endRange);

		return loadPartialMusicFile(filePath, startRange, endRange);
	}

	private LoadMusicResponse loadEntireMusicFile(String filePath) {
		Path musicFilePath = Paths.get(filePath);
		validatePathExistence(musicFilePath);
		if (!musicFilePath.toFile().exists()) {
			throw new BusinessException(ErrorCode.NOT_FOUND_FILE);
		}
		long endRange = getInitialEndRange(musicFilePath);

		return loadPartialMusicFile(filePath, 0, endRange);
	}

	private LoadMusicResponse loadPartialMusicFile(
		final String musicFilePath,
		final long startRange,
		final long endRange
	) {
		byte[] buffer = new byte[1024];

		StreamingResponseBody streamingResponseBody = outputStream -> {
			long currentPosition = startRange;
			try (RandomAccessFile file = new RandomAccessFile(musicFilePath, "r")) {
				file.seek(currentPosition);
				while (currentPosition < endRange) {
					file.read(buffer);
					outputStream.write(buffer);
					currentPosition += buffer.length;
				}
				outputStream.flush();
			} catch (Exception e) {
				throw new BusinessException(ErrorCode.UNABLE_TO_OUTPUT);
			}
		};

		return new LoadMusicResponse(streamingResponseBody, musicFilePath, startRange, endRange);
	}

	private long getLimitedEndRangeIfExceedFileSize(long endRange, Path musicFilePath) {
		long fileSize = getFileSize(musicFilePath);
		if (fileSize < endRange) {
			return fileSize - 1;
		}
		return endRange;
	}

	private String getFilePath(LoadMusicRequest request) {
		String rootPath = musicConfig.getRootPath();
		String userId = String.valueOf(request.userId());
		String postId = String.valueOf(request.postId());

		validateFilePath(rootPath, userId, postId);
		String directoryPath = rootPath + "/" + userId + "/" + postId;
		String fileName = getFileName(directoryPath);
		String realPath = directoryPath + "/" + fileName;
		log.info(realPath);

		return realPath;
	}

	private String getFileName(String directoryPath) {
		log.info(directoryPath);
		File directory = new File(directoryPath);
		String[] fileNames = directory.list();
		log.info(Arrays.toString(fileNames));
		if (fileNames == null) {
			throw new BusinessException(ErrorCode.NOT_FOUND_FILE);
		}

		return fileNames[0];
	}

	private void validateFilePath(String rootPath, String userId, String postId) {
		if (rootPath.isEmpty() || userId.isEmpty() || postId.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_MUSIC_FILE_PATH);
		}
	}

	private void validatePathExistence(Path musicFilePath) {
		File file = musicFilePath.toFile();
		if (!file.exists()) {
			throw new BusinessException(ErrorCode.NOT_FOUND_FILE);
		}
	}

	private long parseRangeToNumericValue(String range) {
		if (!StringUtils.hasText(range)) {
			throw new BusinessException(ErrorCode.INVALID_PARTIAL_RANGE);
		}
		String replacedRange = range.replaceAll("[^0-9]", "");

		return getValueIfNotValidGetDefaultValue(replacedRange);
	}

	private long getValueIfNotValidGetDefaultValue(String replacedRange) {
		try {
			long parsedValue = Long.parseLong(replacedRange);
			log.info("Parsed range to long: {}", parsedValue);
			return parsedValue;
		} catch (NumberFormatException e) {
			log.info("Invalid Value: {}. So, return zero", replacedRange);
			return DEFAULT_RANGE_VALUE;
		}
	}

	private long getInitialEndRange(Path path) {
		long fileSize = getFileSize(path);

		return fileSize - 1L;
	}

	private long getFileSize(Path path) {
		try {
			return Files.size(path);
		} catch (IOException e) {
			log.error("IOException from set file range: {}", "파일 사이즈를 가져올 수 없습니다.", e);
			throw new BusinessException(ErrorCode.UNABLE_TO_GET_FILE_INFO);
		}
	}
}
