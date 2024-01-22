package wave.domain.media.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.domain.vo.Music;

@Getter
@RequiredArgsConstructor
public class MusicFile {

	private final FileId fileId;
	private final Image image;
	private final Music music;
}
