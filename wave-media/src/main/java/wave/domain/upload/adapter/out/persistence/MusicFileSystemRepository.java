package wave.domain.upload.adapter.out.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.config.MusicConfig;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.port.out.persistence.MusicFileRepository;
import wave.domain.media.domain.vo.Music;
import wave.global.error.ErrorCode;
import wave.global.error.exception.FileException;
import wave.global.utils.FileUtils;
import wave.global.utils.MultipartFileUtils;

@RequiredArgsConstructor
@Repository
public class MusicFileSystemRepository implements MusicFileRepository {

	private final MusicConfig musicConfig;

	@Override
	public Music findFileByPath(String uri) {
		Path realPath = Paths.get(uri);
		FileUtils.isPathExist(realPath);
		Path filePath = FileUtils.findFileInPath(realPath);

		return Music.toMusic(filePath, uri);
	}

	@Override
	public MusicFile saveFile(MusicFile musicFile) {
		MultipartFile tmpMultipartFile = musicFile.convertByteDataToMultipartFileByTemp();
		MultipartFileUtils.isPermittedFileSize(tmpMultipartFile, musicConfig.getMaxFileSize());
		Path targetFile = musicFile.createFilePath();
		InputStream tmpFileInputStream = MultipartFileUtils.getInputStream(tmpMultipartFile);

		try {
			Files.copy(tmpFileInputStream, targetFile);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_UPLOAD_FILE, e);
		}

		return musicFile;
	}

	@Override
	public void deleteFileByPath(String path) {
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
			throw new FileException(ErrorCode.NOT_FOUND_FILE_DIRECTORY, e);
		}
	}
}
