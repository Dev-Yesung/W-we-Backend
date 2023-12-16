package wave.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("other_music")
public class OtherMusicPost extends Post {
	private String url;
}
