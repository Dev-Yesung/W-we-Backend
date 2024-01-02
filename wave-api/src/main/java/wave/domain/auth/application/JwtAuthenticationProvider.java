package wave.domain.auth.application;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import lombok.RequiredArgsConstructor;
import wave.domain.auth.dto.JwtAuthenticationToken;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final UserDetailsService customUserDetailsService;
	private final JwtExtractor jwtExtractor;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String rawJwtToken = (String)authentication.getCredentials();
		Map<String, Object> claims = jwtExtractor.extractPayload(rawJwtToken);
		UserDetails userDetails = getUser(claims);
		Collection<? extends GrantedAuthority> authorities = getAuthorities(userDetails);

		return new JwtAuthenticationToken(userDetails, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return ClassUtils.isAssignable(JwtAuthenticationToken.class, authentication);
	}

	private UserDetails getUser(Map<String, Object> claims) {
		String userId = (String)claims.get("id");

		return customUserDetailsService.loadUserByUsername(userId);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(UserDetails userDetails) {
		return userDetails.getAuthorities();
	}
}
