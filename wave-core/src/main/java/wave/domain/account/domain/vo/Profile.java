package wave.domain.account.domain.vo;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Profile {
	String introduction;
	String image;

	public Profile(
		String introduction,
		String image
	) {
		this.introduction = introduction;
		this.image = image;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Profile profile = (Profile)o;
		return Objects.equals(introduction, profile.introduction)
			   && Objects.equals(image, profile.image);
	}

	@Override
	public int hashCode() {
		return Objects.hash(introduction, image);
	}
}
