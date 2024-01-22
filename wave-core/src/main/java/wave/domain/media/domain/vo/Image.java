package wave.domain.media.domain.vo;

import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Image {

	private final String name;
	private final String fileExtension;
	private final String url;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Image image = (Image)o;
		return Objects.equals(name, image.name) && Objects.equals(fileExtension, image.fileExtension)
			   && Objects.equals(url, image.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, fileExtension, url);
	}
}
