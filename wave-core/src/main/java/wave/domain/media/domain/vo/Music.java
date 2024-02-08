package wave.domain.media.domain.vo;

import java.io.RandomAccessFile;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Slf4j
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Music {

	private final String fileName;
	private final String fileExtension;
	private final String mimeType;
	private final long fileSize;
	private final String path;

	public StreamingResponseBody createStreamingResponseBody(String rangeHeader) {
		long[] fileRange = getFileRange(rangeHeader);
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

	public long[] getFileRange(String rangeHeader) {
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
