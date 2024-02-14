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

	public StreamingResponseBody createStreamingResponseBody(String rangeHeader) {
		return music.createStreamingResponseBody(rangeHeader);
	}

	public MultipartFile convertByteDataToMultipartFile() {
		Path fileDataByPath = music.createFilePath();

		return new MediaMultipartFile(fileDataByPath);
	}

	public MultipartFile convertByteDataToMultipartFileByTemp() {
		Path fileDataByPath = music.createFileDataByTemporary();

		return new MediaMultipartFile(fileDataByPath);
	}

	public Path createFilePath() {
		return music.createFilePath();
	}

	public long[] extractFileRange(String rangeHeader) {
		return music.extractFileRange(rangeHeader);
	}

	public String getMusicFileName() {
		return music.getFileName();
	}

	public String getMusicFileExtension() {
		return music.getFileExtension();
	}

	public String getMusicMimeType() {
		return music.getMimeType();
	}

	public long getMusicFileSize() {
		return music.getFileSize();
	}

	public String getPath() {
		return music.getPath();
	}

}
