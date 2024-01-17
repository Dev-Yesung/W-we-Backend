package wave.domain.account.dto;

import java.util.Map;

public record AccessToken(
	String rawAccessToken,
	Map<String, String> claims
) {
}
