package wave.domain.account.dto;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private final Object principal;
	private Object credentials;

	public JwtAuthenticationToken(
		Object credentials
	) {
		super(null);
		this.principal = null;
		this.credentials = credentials;
		this.setAuthenticated(false);
	}

	public JwtAuthenticationToken(
		Object principal,
		Collection<? extends GrantedAuthority> authorities
	) {
		super(authorities);
		this.eraseCredentials();
		this.principal = principal;
		super.setAuthenticated(true);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.credentials = null;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		if (authenticated) {
			throw new IllegalArgumentException("생성자를 통해서만 Authenticated를 true로 설정할 수 있습니다.");
		}
		super.setAuthenticated(false);
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}
}
