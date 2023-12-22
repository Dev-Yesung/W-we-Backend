package wave.global.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import wave.domain.auth.dto.JwtAuthenticationToken;
import wave.domain.auth.application.JwtExtractor;

public class JwtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
	private final AuthenticationFailureHandler authenticationFailureHandler;
	private final JwtExtractor jwtExtractor;

	public JwtAuthenticationProcessingFilter(
		AuthenticationFailureHandler authenticationFailureHandler,
		JwtExtractor jwtExtractor,
		RequestMatcher matcher
	) {
		super(matcher);
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.jwtExtractor = jwtExtractor;
	}

	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) throws AuthenticationException, IOException, ServletException {
		String tokenPayload = request.getHeader(HttpHeaders.AUTHORIZATION);
		String rawToken = jwtExtractor.extractFromHeader(tokenPayload);
		Authentication jwtAuthentication = new JwtAuthenticationToken(rawToken);

		return getAuthenticationManager().authenticate(jwtAuthentication);
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);

		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException failed
	) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
	}
}
