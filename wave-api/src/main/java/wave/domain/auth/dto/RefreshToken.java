package wave.domain.auth.dto;

import java.util.Map;

import lombok.Getter;

public record RefreshToken(
	String rawRefreshToken,
	Map<String, String> claims
) {
}
