package wave.domain.post.domain.vo;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PostContent {

	private String artistName;
	private String songName;
	private String title;
	private String descriptions;

	public PostContent(
		String artistName,
		String songName,
		String title,
		String descriptions
	) {
		this.artistName = artistName;
		this.songName = songName;
		this.title = title;
		this.descriptions = descriptions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PostContent that = (PostContent)o;
		return Objects.equals(artistName, that.artistName) && Objects.equals(songName, that.songName)
			   && Objects.equals(title, that.title) && Objects.equals(descriptions, that.descriptions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(artistName, songName, title, descriptions);
	}
}
