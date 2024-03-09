package wave.domain.media.domain.vo;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import wave.global.utils.FileUtils;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MusicTest {

	@TempDir
	Path testDirectory;
	Path testFilePath;
	Music music;

	@BeforeEach
	void setUp() throws IOException {
		testFilePath = Files.createFile(testDirectory.resolve("tmp.mp3"));
		byte[] data = new byte[1024];
		Files.write(testFilePath, data);

		Path path = testFilePath;
		byte[] fileData = FileUtils.getFileData(path);
		String mimeType = FileUtils.getMimeType(path);
		music = new Music("tmp",
			"mp3",
			mimeType,
			1024L,
			testDirectory.toString(),
			fileData);
	}

	@AfterEach
	void tearDown() throws IOException {
		Files.delete(testFilePath);
	}

	@DisplayName("RangeHeader 값이 없을 때도 StreamingResponseBody를 생성할 수 있다.")
	@NullAndEmptySource
	@ParameterizedTest
	void createStreamingResponseBodyIfRangeHeaderIsEmpty(
		String rangeHeader
	) {
		// when
		StreamingResponseBody streamingResponseBody
			= music.createStreamingResponseBody(rangeHeader);

		// then
		assertThat(streamingResponseBody)
			.isNotNull();
	}

	@DisplayName("RangeHeader의 값이 파일의 크기를 넘어도 파일 전체 크기로 StreamingResponseBody를 생성할 수 있다.")
	@ValueSource(strings = {"0-1025", "300-1025", "-300-512",
		Long.MIN_VALUE + "-1024",
		Long.MIN_VALUE + "-" + Long.MAX_VALUE,
		"0-" + Long.MAX_VALUE})
	@ParameterizedTest
	void createStreamingResponseBodyIfRangeHeaderExceedFileRanges(
		String rangeHeader
	) {
		// when
		StreamingResponseBody streamingResponseBody
			= music.createStreamingResponseBody(rangeHeader);

		// then
		assertThat(streamingResponseBody)
			.isNotNull();
	}

	@DisplayName("RangeHeader의 값이 유효하지 않아도 StreamingResponseBody를 생성할 수 있다.")
	@ValueSource(strings = {"@#$%[]{}+_)(*&^%", "hvoeffhwe", "홎ㄷ랴ㅐㅇ", "-", "-----------"})
	@ParameterizedTest
	void createStreamingResponseBodyIfRangeHeaderIsNotValidFormat(
		String rangeHeader
	) {
		// when
		StreamingResponseBody streamingResponseBody
			= music.createStreamingResponseBody(rangeHeader);

		// then
		assertThat(streamingResponseBody)
			.isNotNull();
	}

	@DisplayName("Music 객체로부터 파일의 Path 객체를 만들어낼 수 있다.")
	@Test
	void getFilePathFromMusicModel() {
		// when
		Path filePath = music.createFilePath();

		// then
		assertThat(filePath.toString())
			.isEqualTo(testFilePath.toString());
	}

}
