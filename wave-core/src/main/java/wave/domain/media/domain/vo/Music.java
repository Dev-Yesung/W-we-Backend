package wave.domain.media.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Music {

	private final String fileName;
	private final String fileExtension;
	private final long fileSize;
	private final String path;
}
