package wave.domain.music.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import wave.config.MusicUploadConfiguration;
import wave.domain.music.dto.UploadMusicDto;
import wave.domain.music.dto.response.UploadMusicResponse;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Transactional
@Service
public class MusicUploadService {
	private final MusicUploadConfiguration musicUploadConfiguration;

	public MusicUploadService(MusicUploadConfiguration musicUploadConfiguration) {
		this.musicUploadConfiguration = musicUploadConfiguration;
		makeDirectory(musicUploadConfiguration.getRootPath());
	}

	public UploadMusicResponse uploadMusic(UploadMusicDto uploadMusicDto) {
		validateFile(uploadMusicDto);
		String directoryPath = getDirectoryPath(uploadMusicDto);
		makeDirectory(directoryPath);
		String fileName = getFileName(uploadMusicDto);
		saveMusicFile(uploadMusicDto, directoryPath, fileName);

		return getUploadMusicResponse(uploadMusicDto, fileName, directoryPath);
	}

	private UploadMusicResponse getUploadMusicResponse(
		UploadMusicDto uploadMusicDto,
		String fileName,
		String directoryPath
	) {
		MultipartFile ownMusicFile = uploadMusicDto.ownMusicFile();
		String fileExtension = getFileExtension(ownMusicFile);

		return new UploadMusicResponse(fileName, fileExtension, directoryPath);
	}

	private void saveMusicFile(UploadMusicDto uploadMusicDto, String directoryPath, String fileName) {
		MultipartFile ownMusicFile = uploadMusicDto.ownMusicFile();
		Path uploadPath = Paths.get(directoryPath + File.separator + fileName);
		try {
			InputStream inputStream = ownMusicFile.getInputStream();
			Files.copy(inputStream, uploadPath);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.UNABLE_TO_UPLOAD);
		}
	}

	private String getDirectoryPath(UploadMusicDto uploadMusicDto) {
		String rootPath = musicUploadConfiguration.getRootPath();
		Long userId = uploadMusicDto.userId();
		Long postId = uploadMusicDto.postId();

		return rootPath + File.separator + userId + File.separator + postId;
	}

	private String getFileName(UploadMusicDto uploadMusicDto) {
		Long postId = uploadMusicDto.postId();
		String fileNameSeparator = musicUploadConfiguration.getFileNameSeparator();
		MultipartFile ownMusicFile = uploadMusicDto.ownMusicFile();
		String originalFileName = ownMusicFile.getName();

		return postId + fileNameSeparator + originalFileName;
	}

	private void makeDirectory(String path) {
		validatePath(path);
		File directory = new File(path);
		boolean isAlreadyExisted = directory.exists();
		if (isAlreadyExisted) {
			return;
		}

		boolean isSuccessToMake = directory.mkdirs();
		if (!isSuccessToMake) {
			throw new BusinessException(ErrorCode.UNABLE_TO_MAKE_DIRECTORY);
		}

	}

	private void validatePath(String path) {
		String trimmedPath = path.trim();
		if (trimmedPath.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_UPLOAD_PATH);
		}
	}

	private void validateFile(UploadMusicDto uploadMusicDto) {
		MultipartFile ownMusicFile = uploadMusicDto.ownMusicFile();
		isValidFileName(ownMusicFile);
		isValidFileSize(ownMusicFile);
		isValidFileExtension(ownMusicFile);
	}

	private void isValidFileName(MultipartFile ownMusicFile) {
		String fileName = ownMusicFile.getName();
		if (fileName.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_FILE_NAME);
		}
	}

	private void isValidFileSize(MultipartFile ownMusicFile) {
		long fileSize = ownMusicFile.getSize();
		long maxFileSize = musicUploadConfiguration.getMaxFileSize();
		if (fileSize > maxFileSize) {
			throw new BusinessException(ErrorCode.EXCEED_MAX_FILE_SIZE);
		}
	}

	private void isValidFileExtension(MultipartFile ownMusicFile) {
		String uploadFileExtension = getFileExtension(ownMusicFile);
		List<String> fileExtensions = musicUploadConfiguration.getFileExtensions();
		fileExtensions.stream()
			.filter(fileExtension -> fileExtension.equals(uploadFileExtension))
			.findAny()
			.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_FILE_EXTENSION));
	}

	private String getFileExtension(MultipartFile ownMusicFile) {
		String[] splitName = ownMusicFile.getName()
			.split("\\.");
		return splitName[1];
	}
}
