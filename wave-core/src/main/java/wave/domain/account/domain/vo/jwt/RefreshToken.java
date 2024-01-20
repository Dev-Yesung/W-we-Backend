package wave.domain.account.domain.vo.jwt;

import java.util.Map;

import wave.domain.account.domain.vo.jwt.AbstractToken;

public class RefreshToken extends AbstractToken {

	public RefreshToken(String rawToken, Map<String, String> claims) {
		super(rawToken, claims);
	}

	public String getRefreshToken() {
		return getRawToken();
	}

	public Map<String, String> claims() {
		return getClaims();
	}
}
