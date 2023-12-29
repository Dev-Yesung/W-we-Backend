package wave.domain.post.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.domain.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("OTHER")
@Entity
public class OtherMusicPost extends Post {
	private String url;

	public OtherMusicPost(String title, String contents, int waves, User user, String url) {
		super(title, contents, waves, user);
		this.url = url;
	}
}
