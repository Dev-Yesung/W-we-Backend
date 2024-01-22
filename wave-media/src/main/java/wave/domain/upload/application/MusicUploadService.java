package wave.domain.upload.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import wave.config.MusicUploadConfiguration;
import wave.domain.media.dto.UploadMusicDto;
import wave.domain.media.dto.response.UploadMusicResponse;
import wave.domain.post.dto.MusicDeleteDto;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

// File을 객체화 시키면 좋을듯..?
@Transactional
@Service
public class MusicUploadService {
	private final MusicUploadConfiguration musicUploadConfiguration;

	public MusicUploadService(MusicUploadConfiguration musicUploadConfiguration) {
		this.musicUploadConfiguration = musicUploadConfiguration;
		makeDirectory(musicUploadConfiguration.getRootPath());
	}

	public UploadMusicResponse uploadMusic(UploadMusicDto uploadMusicDto) {
		Long postId = uploadMusicDto.postId();
		Long userId = uploadMusicDto.userId();
		MultipartFile ownMusicFile = uploadMusicDto.ownMusicFile();

		validateFile(ownMusicFile);
		String path = getDirectoryPath(userId, postId);
		makeDirectory(path);

		String fileName = getConvertedFileName(postId, ownMusicFile);
		saveMusicFile(ownMusicFile, path, fileName);

		return getUploadMusicResponse(userId, postId);
	}

	public void deleteMusic(MusicDeleteDto musicDeleteDto) {
		Long userId = musicDeleteDto.userId();
		Long postId = musicDeleteDto.postId();
		String path = getDirectoryPath(userId, postId);
		removeFiles(path);
	}

	private void removeFiles(String path) {
		Path directoryPath = Paths.get(path);
		try (Stream<Path> fileList = Files.list(directoryPath)) {
			fileList.forEach(file -> {
				String fileDirectory = path + "/" + file.getFileName().toString();
				Path filePath = Paths.get(fileDirectory);
				try {
					Files.delete(filePath);
				} catch (IOException e) {
					throw new BusinessException(ErrorCode.NOT_FOUND_MUSIC_FILE, e);
				}
			});
			Files.delete(directoryPath);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.NOT_FOUND_MUSIC_FILE_DIRECTORY, e);
		}
	}

	private void saveMusicFile(MultipartFile ownMusicFile, String path, String fileName) {
		Path uploadPath = Paths.get(path + File.separator + fileName);
		try {
			InputStream inputStream = ownMusicFile.getInputStream();
			Files.copy(inputStream, uploadPath);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.UNABLE_TO_UPLOAD_MUSIC);
		}
	}

	private String getDirectoryPath(Long userId, Long postId) {
		String rootPath = musicUploadConfiguration.getRootPath();

		return rootPath + "/" + userId + "/" + postId;
	}

	private String getConvertedFileName(long postId, MultipartFile ownMusicFile) {
		String fileNameSeparator = musicUploadConfiguration.getFileNameSeparator();
		String convertedFileName = covertWhiteSpaceToDash(ownMusicFile);

		return postId + fileNameSeparator + convertedFileName;
	}

	private String covertWhiteSpaceToDash(MultipartFile ownMusicFile) {
		String originalFilename = ownMusicFile.getOriginalFilename();

		return originalFilename
			.trim()
			.replaceAll(" ", "-");
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
			throw new BusinessException(ErrorCode.UNABLE_TO_MAKE_MUSIC_FILE_DIRECTORY);
		}

	}

	private void validatePath(String path) {
		String trimmedPath = path.trim();
		if (trimmedPath.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_MUSIC_UPLOAD_PATH);
		}
	}

	private void validateFile(MultipartFile ownMusicFile) {
		isValidFileName(ownMusicFile);
		isValidFileSize(ownMusicFile);
		isValidFileExtension(ownMusicFile);
	}

	private void isValidFileName(MultipartFile ownMusicFile) {
		String fileName = ownMusicFile.getOriginalFilename();
		if (fileName == null || fileName.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_MUSIC_FILE_NAME);
		}
	}

	private void isValidFileSize(MultipartFile ownMusicFile) {
		long fileSize = ownMusicFile.getSize();
		long maxFileSize = musicUploadConfiguration.getMaxFileSize();
		if (fileSize > maxFileSize) {
			throw new BusinessException(ErrorCode.EXCEED_MAX_MUSIC_FILE_SIZE);
		}
	}

	private void isValidFileExtension(MultipartFile ownMusicFile) {
		String uploadFileExtension = getFileExtension(ownMusicFile);
		List<String> fileExtensions = musicUploadConfiguration.getFileExtensions();
		fileExtensions.stream()
			.filter(uploadFileExtension::equals)
			.findAny()
			.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_MUSIC_FILE_EXTENSION));
	}

	private String getFileExtension(MultipartFile ownMusicFile) {
		String fileName = ownMusicFile.getOriginalFilename();
		int length = fileName.length();

		return fileName.substring(length - 3);
	}

	private UploadMusicResponse getUploadMusicResponse(long userId, long postId) {
		String host = musicUploadConfiguration.getHost();
		String url = host + "/" + "api/music/" + userId + "/" + postId;

		return new UploadMusicResponse(url);
	}
}
