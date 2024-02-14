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
import wave.config.ImageConfig;
import wave.domain.media.domain.entity.ImageFile;
import wave.domain.media.domain.port.out.ImageFileRepository;
import wave.domain.media.domain.vo.Image;
import wave.global.error.ErrorCode;
import wave.global.error.exception.FileException;
import wave.global.utils.FileUtils;
import wave.global.utils.MultipartFileUtils;

@RequiredArgsConstructor
@Repository
public class ImageFileSystemRepository implements ImageFileRepository {

	private final ImageConfig imageConfig;

	@Override
	public Image findFileByPath(String uri) {
		Path realPath = Paths.get(uri);
		FileUtils.isPathExist(realPath);

		return Image.toImage(realPath, uri);
	}

	@Override
	public void saveFile(ImageFile imageFile) {
		MultipartFile multipartFile = imageFile.convertByteDataToMultipartFile();
		MultipartFileUtils.isPermittedFileSize(multipartFile, imageConfig.getMaxFileSize());
		Path fileDataByPath = imageFile.createFileDataByPath();
		InputStream inputStream = MultipartFileUtils.getInputStream(multipartFile);

		try {
			Files.copy(inputStream, fileDataByPath);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_UPLOAD_FILE, e);
		}
	}

	@Override
	public void deleteFileByPath(String uri) {
		Path directoryPath = Paths.get(uri);
		try (Stream<Path> files = Files.list(directoryPath)) {
			files.forEach(file -> {
				String fileDirectory = uri + "/" + file.getFileName().toString();
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
