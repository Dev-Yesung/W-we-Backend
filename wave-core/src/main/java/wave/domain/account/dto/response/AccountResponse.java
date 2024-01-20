package wave.domain.account.dto.response;

import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.jwt.AccessToken;
import wave.domain.account.domain.vo.jwt.RefreshToken;

public record AccountResponse(
	long id,
	String email,
	String accessToken,
	String refreshToken
) {
	public static AccountResponse from(
		User savedUser,
		AccessToken accessToken,
		RefreshToken refreshToken
	) {
		Long id = savedUser.getId();
		String email = savedUser.getEmail();
		String rawAccessToken = accessToken.getAccessToken();
		String rawRefreshToken = refreshToken.getRefreshToken();

		return new AccountResponse(id, email, rawAccessToken, rawRefreshToken);
	}
}
