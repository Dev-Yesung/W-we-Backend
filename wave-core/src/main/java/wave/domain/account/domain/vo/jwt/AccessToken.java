package wave.domain.account.domain.vo.jwt;

import java.util.Map;

public class AccessToken extends AbstractToken {

	public AccessToken(String rawToken, Map<String, String> claims) {
		super(rawToken, claims);
	}

	public String getAccessToken() {
		return super.getRawToken();
	}

	public Map<String, String> claims() {
		return getClaims();
	}
}
