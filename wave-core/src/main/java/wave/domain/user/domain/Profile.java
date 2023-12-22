package wave.domain.user.domain;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import jakarta.persistence.Embeddable;

@Embeddable
public class Profile {
	String introduction;
	String image;

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
