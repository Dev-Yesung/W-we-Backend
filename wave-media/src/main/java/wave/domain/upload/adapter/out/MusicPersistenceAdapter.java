package wave.domain.upload.adapter.out;

import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.config.MusicConfig;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.port.out.LoadMusicPort;
import wave.domain.media.domain.port.out.UpdateMusicPort;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Music;
import wave.domain.media.dto.FileDeleteDto;
import wave.domain.media.dto.FileUploadDto;
import wave.domain.media.domain.port.out.MusicRepository;
import wave.global.common.PersistenceAdapter;
import wave.global.utils.FileUtils;

@RequiredArgsConstructor
@PersistenceAdapter
public class MusicPersistenceAdapter
	implements UpdateMusicPort, LoadMusicPort {

	private final MusicConfig musicConfig;
	private final MusicRepository musicRepository;

	@Override
	public void saveFile(FileUploadDto fileUploadDto) {
		MusicFile musicFile = getFileEntity(fileUploadDto);
		MultipartFile file = fileUploadDto.file();

		musicRepository.saveFile(musicFile, file);
	}

	private MusicFile getFileEntity(FileUploadDto fileUploadDto) {
		FileId fileId = getFileId(fileUploadDto);
		Music music = getMusicVO(fileUploadDto);

		return new MusicFile(fileId, music);
	}

	@Override
	public void deleteFile(FileDeleteDto fileDeleteDto) {
		Long userId = fileDeleteDto.userId();
		Long postId = fileDeleteDto.postId();
		String path = getPath(userId, postId);

		musicRepository.deleteFile(path);
	}

	private Music getMusicVO(FileUploadDto fileUploadDto) {
		Long userId = fileUploadDto.userId();
		Long postId = fileUploadDto.postId();
		MultipartFile file = fileUploadDto.file();

		String pureFileName = FileUtils.getPureFileName(file);
		String fileExtension = FileUtils.getFileExtension(file);
		long fileSize = FileUtils.getFileSize(file);
		String normalizedPath = getPath(userId, postId);

		return new Music(pureFileName, fileExtension, fileSize, normalizedPath);
	}

	private FileId getFileId(FileUploadDto fileUploadDto) {
		Long userId = fileUploadDto.userId();
		Long postId = fileUploadDto.postId();

		return new FileId(userId, postId);
	}

	private String getPath(Long userId, Long postId) {
		String filePath = getFilePath(userId, postId);

		return FileUtils.getNormalizedPath(filePath);
	}

	private String getFilePath(Long userId, Long postId) {
		String rootPath = musicConfig.getRootPath();

		return rootPath + "/" + userId + "/" + postId;
	}

}
