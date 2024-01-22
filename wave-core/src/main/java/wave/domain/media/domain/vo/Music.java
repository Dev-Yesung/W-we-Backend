package wave.domain.media.domain.vo;

import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Music {

	private final String title;
	private final String fileExtension;
	private final String url;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Music music = (Music)o;
		return Objects.equals(title, music.title) && Objects.equals(fileExtension, music.fileExtension)
			   && Objects.equals(url, music.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, fileExtension, url);
	}
}
