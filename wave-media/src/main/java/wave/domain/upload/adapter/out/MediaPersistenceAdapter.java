package wave.domain.upload.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.config.ImageConfig;
import wave.config.MusicConfig;
import wave.domain.media.domain.entity.ImageFile;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.port.out.ImageFileRepository;
import wave.domain.media.domain.port.out.LoadMediaPort;
import wave.domain.media.domain.port.out.MusicFileRepository;
import wave.domain.media.domain.port.out.UpdateMediaPort;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.domain.vo.Music;
import wave.domain.media.dto.FileDeleteDto;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.domain.media.dto.request.LoadMusicRequest;
import wave.global.common.PersistenceAdapter;
import wave.global.utils.FileUtils;

@RequiredArgsConstructor
@PersistenceAdapter
public class MediaPersistenceAdapter implements UpdateMediaPort, LoadMediaPort {

	private final MusicFileRepository musicFileRepository;
	private final ImageFileRepository imageFileRepository;
	private final MusicConfig musicConfig;
	private final ImageConfig imageConfig;

	@Override
	public MusicFile loadMusicFile(LoadMusicRequest request) {
		Long userId = request.userId();
		Long postId = request.postId();
		FileId fileId = new FileId(userId, postId);

		String path = getPath(fileId, musicConfig.getRootPath());
		Music music = musicFileRepository.findFileByPath(path);

		return new MusicFile(fileId, music);
	}

	@Override
	public ImageFile loadImageFile() {
		return null;
	}

	@Override
	public void saveFile(MediaFileUploadMessage mediaFileUploadMessage) {
		MusicFile musicFile = getMusicFile(mediaFileUploadMessage);
		ImageFile imageFile = getImageFile(mediaFileUploadMessage);

		musicFileRepository.saveFile(musicFile);
		imageFileRepository.saveFile(imageFile);
	}

	@Override
	public void deleteFile(FileDeleteDto fileDeleteDto) {
		Long userId = fileDeleteDto.userId();
		Long postId = fileDeleteDto.postId();
		FileId fileId = new FileId(userId, postId);
		String musicPath = getPath(fileId, musicConfig.getRootPath());
		String imagePath = getPath(fileId, imageConfig.getRootPath());

		musicFileRepository.deleteFileByPath(musicPath);
		imageFileRepository.deleteFileByPath(imagePath);
	}

	private MusicFile getMusicFile(MediaFileUploadMessage mediaFileUploadMessage) {
		FileId fileId = mediaFileUploadMessage.fileId();
		Music before = mediaFileUploadMessage.music();
		String path = getPath(fileId, musicConfig.getRootPath());
		Music newMusic = Music.toMusic(before, path);

		return new MusicFile(fileId, newMusic);
	}

	private ImageFile getImageFile(MediaFileUploadMessage mediaFileUploadMessage) {
		FileId fileId = mediaFileUploadMessage.fileId();
		Image before = mediaFileUploadMessage.image();
		String path = getPath(fileId, imageConfig.getRootPath());
		Image newImage = Image.toImage(before, path);

		return new ImageFile(fileId, newImage);
	}

	private String getPath(FileId fileId, String rootPath) {
		Long userId = fileId.getUserId();
		Long postId = fileId.getPostId();

		return FileUtils.getNormalizedPath(rootPath + "/" + userId + "/" + postId);
	}

}
