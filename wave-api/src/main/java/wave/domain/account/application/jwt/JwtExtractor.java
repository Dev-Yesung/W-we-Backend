package wave.domain.account.application.jwt;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import wave.config.JwtConfig;
import wave.domain.account.exception.JwtExpiredTokenException;

@RequiredArgsConstructor
@Component
public class JwtExtractor {
	public static String HEADER_PREFIX = "Bearer ";
	private final JwtConfig jwtConfiguration;

	public String extractFromHeader(String payload) {
		if (!StringUtils.hasText(payload)) {
			throw new AuthenticationServiceException("Authorization Header는 비어있을 수 없습니다.");
		}
		if (payload.length() < HEADER_PREFIX.length()) {
			throw new AuthenticationServiceException("Authorization Header의 크기가 잘못 됐습니다.");
		}

		return payload.substring(HEADER_PREFIX.length());
	}

	public Map<String, Object> extractPayload(String rawToken) {
		try {
			Jws<Claims> claimsJws = Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(rawToken);
			Claims payload = claimsJws.getPayload();
			Map<String, Object> payloadMap = new HashMap<>();
			payloadMap.put("subject", payload.getSubject());
			payloadMap.put("id", payload.get("id"));
			payloadMap.put("email", payload.get("email"));
			payloadMap.put("role", payload.get("role"));
			payloadMap.put("scopes", payload.get("scopes"));

			return payloadMap;
		} catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
			throw new BadCredentialsException("올바르지 않은 형식의 JWT 입니다.");
		} catch (ExpiredJwtException e) {
			throw new JwtExpiredTokenException("기간이 만료된 JWT 입니다.");
		}
	}

	private SecretKey getSecretKey() {
		String rawTokenSecretKey = jwtConfiguration.getTokenSecret();
		byte[] tokenSecretBytes = rawTokenSecretKey.getBytes();
		String algorithmType = jwtConfiguration.getAlgorithmType();

		return new SecretKeySpec(tokenSecretBytes, algorithmType);
	}
}
