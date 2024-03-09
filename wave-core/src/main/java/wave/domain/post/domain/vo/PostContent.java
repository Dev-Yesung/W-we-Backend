package wave.domain.post.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
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
}
