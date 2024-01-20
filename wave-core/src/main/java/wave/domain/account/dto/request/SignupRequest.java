package wave.domain.account.dto.request;

import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Profile;
import wave.domain.account.domain.vo.Role;

public record SignupRequest(
	String email,
	String nickname
) {
	public static User of(SignupRequest request) {
		String email = request.email();
		String nickname = request.nickname();
		Profile profile = new Profile("", "");

		return new User(email, nickname, profile, Role.USER);
	}
}
