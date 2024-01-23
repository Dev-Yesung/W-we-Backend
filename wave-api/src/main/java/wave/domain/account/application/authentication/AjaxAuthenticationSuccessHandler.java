package wave.domain.account.application.authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import wave.domain.account.application.jwt.JwtUtils;
import wave.domain.account.domain.vo.jwt.AccessToken;
import wave.domain.account.domain.vo.jwt.RefreshToken;
import wave.domain.account.domain.entity.User;

@RequiredArgsConstructor
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final ObjectMapper objectMapper;
	private final JwtUtils jwtTokenFactory;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException, ServletException {
		User user = (User)authentication.getPrincipal();

		AccessToken accessToken = jwtTokenFactory.createAccessToken(user);
		RefreshToken refreshToken = jwtTokenFactory.createRefreshToken(user);
		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put("accessToken", accessToken.getAccessToken());
		tokenMap.put("refreshToken", refreshToken.getRefreshToken());

		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		objectMapper.writeValue(response.getWriter(), tokenMap);

		clearAuthenticationAttributes(request);
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
