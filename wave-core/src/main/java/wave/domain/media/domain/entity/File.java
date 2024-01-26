package wave.domain.media.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.vo.FileId;

@Getter
@RequiredArgsConstructor
public abstract class File {

	private final FileId fileId;
}
