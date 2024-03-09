package wave.domain.media.domain.entity;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.MediaMultipartFile;
import wave.domain.media.domain.vo.Music;

public class MusicFile extends AbstractMediaFile {

	private final Music music;

	public MusicFile(FileId fileId, Music music) {
		super(fileId);
		this.music = music;
	}

	public MultipartFile convertByteDataToMultipartFileByTemp() {
		Path fileDataByPath = music.createFileDataByTemporary();

		return new MediaMultipartFile(fileDataByPath);
	}

	public Path createFilePath() {
		return music.createFilePath();
	}

	public String createUrl(String host, String port, String urlPath) {
		FileId fileId = getFileId();

		return host + ":" + port
			   + "/" + urlPath
			   + "/" + fileId.getPostId();
	}

	public String getPath() {
		return music.getPath();
	}

	public Music getMusic() {
		return this.music;
	}

}
