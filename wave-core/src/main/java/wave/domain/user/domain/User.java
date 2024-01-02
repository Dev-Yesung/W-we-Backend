package wave.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;
	private String nickname;
	@Embedded
	private Profile profile;

	@Enumerated(EnumType.STRING)
	private Role role;

	public User(
		String email,
		String nickname,
		Profile profile,
		Role role
	) {
		this.email = email;
		this.nickname = nickname;
		this.profile = profile;
		this.role = role;
	}
}
