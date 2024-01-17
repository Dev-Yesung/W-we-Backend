package wave.domain.auth.application;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import wave.domain.auth.exception.AuthMethodNotSupportedException;
import wave.domain.auth.exception.JwtExpiredTokenException;
import wave.global.error.ErrorCode;
import wave.global.error.ErrorResponse;

@RequiredArgsConstructor
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception
	) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		if (exception instanceof BadCredentialsException) {
			objectMapper.writeValue(response.getWriter(), ErrorResponse.of(ErrorCode.INVALID_CERTIFICATION_CODE));
		} else if (exception instanceof JwtExpiredTokenException) {
			objectMapper.writeValue(response.getWriter(), ErrorResponse.of(ErrorCode.EXPIRED_JWT));
		} else if (exception instanceof AuthMethodNotSupportedException) {
			objectMapper.writeValue(response.getWriter(), ErrorResponse.of(ErrorCode.AUTH_METHOD_NOT_SUPPORTED));
		}

		objectMapper.writeValue(response.getWriter(), ErrorResponse.of(ErrorCode.AUTHENTICATION_FAIL));
	}
}
