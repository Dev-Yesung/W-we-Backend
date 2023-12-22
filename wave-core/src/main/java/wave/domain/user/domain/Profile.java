package wave.domain.user.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Profile {
	String introduction;
	String image;
}
