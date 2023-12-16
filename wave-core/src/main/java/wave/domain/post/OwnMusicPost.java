package wave.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("OWN")
public class OwnMusicPost extends Post {
	private String filePath;
}
