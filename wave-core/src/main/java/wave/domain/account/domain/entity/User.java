package wave.domain.account.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.domain.account.domain.vo.Profile;
import wave.domain.account.domain.vo.Role;
import wave.global.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Users")
@Entity
public class User extends BaseEntity {
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
