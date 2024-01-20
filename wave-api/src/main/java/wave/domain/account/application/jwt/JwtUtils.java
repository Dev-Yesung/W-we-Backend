package wave.domain.account.application.jwt;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import wave.config.JwtConfig;
import wave.domain.account.domain.vo.jwt.AccessToken;
import wave.domain.account.domain.vo.jwt.RefreshToken;
import wave.domain.account.domain.vo.jwt.TokenType;
import wave.domain.account.domain.entity.User;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@Component
public class JwtUtils {

	private final JwtConfig jwtConfiguration;

	public AccessToken createAccessToken(User user) {
		if (StringUtils.isBlank(user.getEmail())) {
			throw new EntityException(ErrorCode.NOT_FOUND_USER_EMAIL);
		}
		if (user.getRole() == null) {
			throw new EntityException(ErrorCode.NOT_FOUND_USER_ROLL);
		}

		String issuer = jwtConfiguration.getIssuer();
		Map<String, String> claims = getClaims(user, TokenType.ACCESS_TOKEN);
		LocalDateTime currentTime = LocalDateTime.now();
		Long accessTokenExpirySeconds = jwtConfiguration.getAccessTokenExpirySeconds();
		SecretKey accessTokenSecretKey = getSecretKey();

		String accessToken = Jwts.builder()
			.subject("WaveApiToken")
			.issuer(issuer)
			.issuedAt(Date.from(currentTime
				.atZone(ZoneId.systemDefault())
				.toInstant()))
			.expiration(Date.from(currentTime
				.plusSeconds(accessTokenExpirySeconds)
				.atZone(ZoneId.systemDefault())
				.toInstant()))
			.claims(claims)
			.signWith(accessTokenSecretKey)
			.compact();

		return new AccessToken(accessToken, claims);
	}

	public RefreshToken createRefreshToken(User user) {
		if (StringUtils.isBlank(user.getEmail())) {
			throw new EntityException(ErrorCode.NOT_FOUND_USER_EMAIL);
		}

		String issuer = jwtConfiguration.getIssuer();
		Map<String, String> claims = getClaims(user, TokenType.REFRESH_TOKEN);
		LocalDateTime currentTime = LocalDateTime.now();
		Long refreshTokenExpirySeconds = jwtConfiguration.getRefreshTokenExpirySeconds();
		SecretKey refreshTokenSecretKey = getSecretKey();

		String refreshToken = Jwts.builder()
			.subject("WaveRefreshToken")
			.issuer(issuer)
			.issuedAt(Date.from(currentTime
				.atZone(ZoneId.systemDefault())
				.toInstant()))
			.expiration(Date.from(currentTime
				.plusSeconds(refreshTokenExpirySeconds)
				.atZone(ZoneId.systemDefault())
				.toInstant()))
			.id(UUID.randomUUID().toString())
			.claims(claims)
			.signWith(refreshTokenSecretKey)
			.compact();

		return new RefreshToken(refreshToken, claims);

	}

	private Map<String, String> getClaims(User user, TokenType scope) {
		Map<String, String> claims = new HashMap<>();
		claims.put("id", String.valueOf(user.getId()));
		claims.put("email", user.getEmail());
		claims.put("role", user.getRole().name());
		claims.put("scopes", scope.getName());

		return claims;
	}

	private SecretKey getSecretKey() {
		String rawTokenSecretKey = jwtConfiguration.getTokenSecret();
		byte[] tokenSecretBytes = rawTokenSecretKey.getBytes();
		String algorithmType = jwtConfiguration.getAlgorithmType();

		return new SecretKeySpec(tokenSecretBytes, algorithmType);
	}
}
