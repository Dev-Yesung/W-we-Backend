package wave.global.filter;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import wave.domain.account.dto.request.LoginRequest;

@Slf4j
public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;
	private final ObjectMapper objectMapper;

	public AjaxLoginProcessingFilter(
		String defaultFilterProcessesUrl,
		AuthenticationSuccessHandler authenticationSuccessHandler,
		AuthenticationFailureHandler authenticationFailureHandler,
		ObjectMapper objectMapper
	) {
		super(defaultFilterProcessesUrl);
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.objectMapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) throws AuthenticationException, IOException, ServletException {
		if (!isPostMethodRequest(request)) {
			if (log.isDebugEnabled()) {
				log.debug("지원하지 않는 Authentication method(요청된 method: {}) 입니다.", request.getMethod());
			}
			throw new AuthenticationServiceException("지원하지 않는 Authentication method 입니다.");
		}

		LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
		if (!isValidLoginRequest(loginRequest)) {
			throw new AuthenticationServiceException("이메일이나 패스워드가 입력되지 않았습니다.");
		}

		String email = loginRequest.email();
		String certificationNumber = loginRequest.certificationCode();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, certificationNumber);
		AuthenticationManager authenticationManager = getAuthenticationManager();

		return authenticationManager.authenticate(token);
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException, ServletException {
		authenticationSuccessHandler.onAuthenticationSuccess(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException failed
	) throws IOException, ServletException {
		authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
	}

	private boolean isPostMethodRequest(
		HttpServletRequest request
	) {
		String postMethod = HttpMethod.POST.name();
		String httpRequestMethod = request.getMethod();

		return postMethod.equals(httpRequestMethod);
	}

	private boolean isValidLoginRequest(
		LoginRequest loginRequest
	) {
		String email = loginRequest.email();
		String certificationNumber = loginRequest.certificationCode();

		return StringUtils.hasText(email) && StringUtils.hasText(certificationNumber);
	}
}
