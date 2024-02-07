package wave.domain.account.application.authentication;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.port.out.LoadAccountPort;
import wave.domain.account.domain.port.out.AccountCache;
import wave.domain.account.domain.vo.CertificationType;
import wave.domain.account.domain.vo.Role;
import wave.domain.account.domain.entity.User;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
	private final AccountCache accountCache;
	private final LoadAccountPort accountLoadPort;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "authentication 정보가 없습니다.");
		String email = (String)authentication.getPrincipal();

		String foundCertificationCode = accountCache.getCertificationCode(CertificationType.LOGIN, email)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CERTIFICATION_CODE));
		String certificationCode = (String)authentication.getCredentials();
		if (!foundCertificationCode.equals(certificationCode)) {
			throw new BadCredentialsException("이메일 또는 인증코드가 일치하지 않아 로그인에 실패했습니다.");
		}

		User foundUser = accountLoadPort.findAccountByEmail(email)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_USER));
		Role userRole = foundUser.getRole();
		if (userRole == null) {
			throw new InsufficientAuthenticationException("유저에게 Role이 존재하지 않습니다.");
		}
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userRole.name()));

		return new UsernamePasswordAuthenticationToken(foundUser, null, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return ClassUtils.isAssignable(UsernamePasswordAuthenticationToken.class, authentication);
	}
}
