package wave.domain.media.domain.entity;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.Getter;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Music;

@Getter
public class MusicFile extends File {

	private final Music music;

	public MusicFile(FileId fileId, Music music) {
		super(fileId);
		this.music = music;
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

	public long[] getFileRange(String rangeHeader) {
		return music.getFileRange(rangeHeader);
	}

	public StreamingResponseBody createStreamingResponseBody(String rangeHeader) {
		return music.createStreamingResponseBody(rangeHeader);
	}

}
