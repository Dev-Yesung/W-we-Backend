package wave.domain.auth.application;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import lombok.RequiredArgsConstructor;
import wave.domain.auth.dto.JwtAuthenticationToken;
import wave.domain.user.domain.User;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;
import wave.domain.user.infra.UserRepository;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final UserRepository userRepository;
	private final JwtExtractor jwtExtractor;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String rawJwtToken = (String)authentication.getCredentials();
		Map<String, Object> claims = jwtExtractor.extractPayload(rawJwtToken);
		User user = getUser(claims);
		List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

		return new JwtAuthenticationToken(user, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return ClassUtils.isAssignable(JwtAuthenticationToken.class, authentication);
	}

	private User getUser(Map<String, Object> claims) {
		String idFromClaims = (String)claims.get("id");
		Long userId = Long.valueOf(idFromClaims);

		return userRepository.findById(userId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_USER));
	}

	private List<SimpleGrantedAuthority> getAuthorities(Map<String, Object> claims) {
		String role = (String)claims.get("role");

		return List.of(new SimpleGrantedAuthority(role));
	}
}
