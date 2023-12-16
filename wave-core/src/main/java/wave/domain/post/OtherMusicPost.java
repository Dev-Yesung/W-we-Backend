package wave.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("OTHER")
public class OtherMusicPost extends Post {
	private String url;
}
