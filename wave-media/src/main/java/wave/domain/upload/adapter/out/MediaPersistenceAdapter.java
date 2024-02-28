package wave.domain.upload.adapter.out;

import static wave.domain.media.domain.vo.MediaUploadStatus.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wave.config.ImageConfig;
import wave.config.MusicConfig;
import wave.config.ServerConfig;
import wave.domain.media.domain.entity.ImageFile;
import wave.domain.media.domain.entity.MusicFile;
import wave.domain.media.domain.entity.StreamingSession;
import wave.domain.media.domain.port.out.LoadMediaPort;
import wave.domain.media.domain.port.out.UpdateMediaPort;
import wave.domain.media.domain.port.out.persistence.ImageFileRepository;
import wave.domain.media.domain.port.out.persistence.MusicFileRepository;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.media.domain.vo.Music;
import wave.domain.media.dto.FileDeleteDto;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.domain.media.dto.StreamingSessionInfo;
import wave.domain.media.dto.request.LoadImageRequest;
import wave.domain.media.dto.request.LoadMusicRequest;
import wave.domain.post.domain.entity.Post;
import wave.domain.streaming.adapter.out.persistence.StreamingSessionJpaRepository;
import wave.domain.upload.adapter.out.persistence.PostJpaRepository;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;
import wave.global.utils.FileUtils;

@Slf4j
@RequiredArgsConstructor
@PersistenceAdapter
public class MediaPersistenceAdapter implements UpdateMediaPort, LoadMediaPort {

	private final MusicFileRepository musicFileRepository;
	private final ImageFileRepository imageFileRepository;
	private final StreamingSessionJpaRepository streamingSessionJpaRepository;
	private final PostJpaRepository postJpaRepository;

	private final MusicConfig musicConfig;
	private final ImageConfig imageConfig;
	private final ServerConfig serverConfig;

	@Override
	public MusicFile loadMusicFile(LoadMusicRequest request) {
		Long postId = request.postId();
		Post post = postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));

		Long authorId = post.getUser().getId();
		FileId fileId = new FileId(authorId, postId);

		String path = getPath(fileId, musicConfig.getRootPath());
		Music music = musicFileRepository.findFileByPath(path);

		return new MusicFile(fileId, music);
	}

	@Override
	public ImageFile loadImageFile(LoadImageRequest request) {
		Long postId = request.postId();
		Post post = postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));

		Long authorId = post.getUser().getId();
		FileId fileId = new FileId(authorId, postId);

		String path = getPath(fileId, imageConfig.getRootPath());
		Image image = imageFileRepository.findFileByPath(path);

		return new ImageFile(fileId, image);
	}

	@Override
	public MediaUrl saveFile(MediaFileUploadMessage mediaFileUploadMessage) {
		MusicFile musicFile = getMusicFile(mediaFileUploadMessage);
		ImageFile imageFile = getImageFile(mediaFileUploadMessage);

		MusicFile savedMusicFile = musicFileRepository.saveFile(musicFile);
		ImageFile savedImageFile = imageFileRepository.saveFile(imageFile);

		return getMediaUrl(savedMusicFile, savedImageFile);
	}

	@Override
	public FileId deleteFile(FileDeleteDto fileDeleteDto) {
		Long userId = fileDeleteDto.userId();
		Long postId = fileDeleteDto.postId();
		FileId fileId = new FileId(userId, postId);
		String musicPath = getPath(fileId, musicConfig.getRootPath());
		String imagePath = getPath(fileId, imageConfig.getRootPath());

		musicFileRepository.deleteFileByPath(musicPath);
		imageFileRepository.deleteFileByPath(imagePath);

		return fileId;
	}

	@Override
	public void saveStreamingSession(StreamingSessionInfo sessionInfo) {
		Long postId = sessionInfo.postId();
		Long userId = sessionInfo.userId();
		long startMilliSec = sessionInfo.startMilliSec();
		long endMilliSec = sessionInfo.endMilliSec();
		String ipAddress = sessionInfo.ipAddress();
		StreamingSession newSession = new StreamingSession(postId, userId, startMilliSec, endMilliSec, ipAddress);
		streamingSessionJpaRepository.save(newSession);
	}

	private MusicFile getMusicFile(MediaFileUploadMessage mediaFileUploadMessage) {
		FileId fileId = mediaFileUploadMessage.fileId();
		Music requestMusic = mediaFileUploadMessage.music();
		String path = getPath(fileId, musicConfig.getRootPath());
		Music newMusic = Music.toMusic(requestMusic, path);

		return new MusicFile(fileId, newMusic);
	}

	private ImageFile getImageFile(MediaFileUploadMessage mediaFileUploadMessage) {
		FileId fileId = mediaFileUploadMessage.fileId();
		Image requestImage = mediaFileUploadMessage.image();
		String path = getPath(fileId, imageConfig.getRootPath());
		Image newImage = Image.toImage(requestImage, path);

		return new ImageFile(fileId, newImage);
	}

	private String getPath(FileId fileId, String rootPath) {
		Long userId = fileId.getUserId();
		Long postId = fileId.getPostId();

		return FileUtils.getNormalizedPath(rootPath + "/" + userId + "/" + postId);
	}

	private MediaUrl getMediaUrl(MusicFile savedMusicFile, ImageFile savedImageFile) {
		String host = serverConfig.getHost();
		String port = serverConfig.getPort();
		String imageUrlPath = imageConfig.getUrlPath();
		String musicUrlPath = musicConfig.getUrlPath();

		String imageUrl = savedImageFile.createUrl(host, port, imageUrlPath);
		String musicUrl = savedMusicFile.createUrl(host, port, musicUrlPath);


		return new MediaUrl(imageUrl, musicUrl, COMPLETED);
	}

}
