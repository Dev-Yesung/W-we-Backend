package wave.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wave.domain.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("OWN")
@Entity
public class OwnMusicPost extends Post {
	private String filePath;

	public OwnMusicPost(String title, String contents, int waves, User user, String filePath) {
		super(title, contents, waves, user);
		this.filePath = filePath;
	}
}
