package wave.domain.media.domain.entity;

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

	public String getPath() {
		return music.getPath();
	}

	public String getMusicFileName() {
		return music.getFileName();
	}

	public String getMusicFileExtension() {
		return music.getFileExtension();
	}
}
