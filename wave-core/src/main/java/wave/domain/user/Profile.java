package wave.domain.user;

import jakarta.persistence.Embeddable;

@Embeddable
public class Profile {
	String introduction;
	String image;
}
