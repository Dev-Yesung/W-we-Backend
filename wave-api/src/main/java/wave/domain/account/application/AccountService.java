package wave.domain.account.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import wave.config.CacheConfig;
import wave.domain.account.domain.AccountLoadPort;
import wave.domain.account.domain.AccountUpdatePort;
import wave.domain.account.domain.Certification;
import wave.domain.account.dto.AccessToken;
import wave.domain.account.dto.RefreshToken;
import wave.domain.account.dto.request.CertificationRequest;
import wave.domain.account.dto.request.CertificationVerifyRequest;
import wave.domain.account.dto.request.SignupRequest;
import wave.domain.account.dto.response.AccountResponse;
import wave.domain.account.dto.response.CertificationResponse;
import wave.domain.auth.application.JwtFactory;
import wave.domain.auth.domain.CertificationCodeFactory;
import wave.domain.mail.CertificationType;
import wave.domain.user.domain.Profile;
import wave.domain.user.domain.Role;
import wave.domain.user.domain.User;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

	private final AccountUpdatePort accountUpdatePort;
	private final AccountLoadPort accountLoadPort;

	private final CacheConfig cacheConfig;
	private final CertificationCodeFactory certificationCodeFactory;
	private final JwtFactory jwtFactory;

	@Transactional(readOnly = true)
	public void checkDuplicateEmail(String email) {
		accountLoadPort.checkDuplicateEmail(email);
	}

	@Transactional(readOnly = true)
	public CertificationResponse requestCertificationCode(HttpSession session, CertificationRequest request) {
		Certification certification = createCertification(request);
		accountUpdatePort.publishCertificationEvent(certification);
		int limitSeconds = cacheCertificationCode(session, certification);

		return new CertificationResponse(limitSeconds);
	}

	@Transactional(readOnly = true)
	public void verifyCertificationCode(HttpSession session, CertificationVerifyRequest request) {
		Certification certification = getCertification(request);
		String certificationCode = findCertificationCode(session, certification);
		certification.validateCode(certificationCode);
		removeCachedCertificationCode(session, certification);
	}

	public AccountResponse signup(HttpSession session, SignupRequest request) {
		String email = request.email();
		isVerifiedCertification(session, email);

		User newUser = getNewUser(request);
		User savedUser = accountUpdatePort.saveAccount(newUser);
		accountUpdatePort.publishNewAccountEvent(savedUser);

		return getAccountResponse(savedUser);
	}

	private Certification createCertification(CertificationRequest request) {
		String email = request.email();
		String typeName = request.typeName();
		String randomCode = certificationCodeFactory.createRandomCode();
		CertificationType certificationType = CertificationType.getCertificationType(typeName);

		return new Certification(certificationType, email, randomCode);
	}

	private int cacheCertificationCode(HttpSession session, Certification certification) {
		String sessionKey = getSessionKey(certification);
		String randomCode = certification.getRandomCode();
		int limitSeconds = cacheConfig.getSignupLimitSeconds();

		session.setAttribute(sessionKey, randomCode);
		session.setMaxInactiveInterval(limitSeconds);

		return limitSeconds;
	}

	private Certification getCertification(CertificationVerifyRequest request) {
		String typeName = request.typeName();
		CertificationType certificationType = CertificationType.getCertificationType(typeName);
		String email = request.email();
		String certificationCode = request.certificationCode();

		return new Certification(certificationType, email, certificationCode);
	}

	private String findCertificationCode(HttpSession session, Certification certification) {
		String sessionKey = getSessionKey(certification);

		return (String)Optional.ofNullable(session.getAttribute(sessionKey))
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CERTIFICATION_CODE));
	}

	private void removeCachedCertificationCode(HttpSession session, Certification certification) {
		String sessionKey = getSessionKey(certification);
		session.removeAttribute(sessionKey);
	}

	private void isVerifiedCertification(HttpSession session, String email) {
		String typeName = CertificationType.SIGNUP.getName();
		String sessionKey = typeName + ":" + email;
		String certificationCode = (String)session.getAttribute(sessionKey);
		if (certificationCode != null) {
			throw new BusinessException(ErrorCode.NOT_VERIFIED_CERTIFICATION_CODE);
		}
	}

	private User getNewUser(SignupRequest request) {
		String email = request.email();
		String nickname = request.nickname();
		Profile profile = new Profile("", "");

		return new User(email, nickname, profile, Role.USER);
	}

	private AccountResponse getAccountResponse(User savedUser) {
		Long id = savedUser.getId();
		String email = savedUser.getEmail();
		AccessToken accessToken = jwtFactory.createAccessToken(savedUser);
		String rawAccessToken = accessToken.rawAccessToken();
		RefreshToken refreshToken = jwtFactory.createRefreshToken(savedUser);
		String rawRefreshToken = refreshToken.rawRefreshToken();

		return new AccountResponse(id, email, rawAccessToken, rawRefreshToken);
	}

	private String getSessionKey(Certification certification) {
		CertificationType certificationType = certification.getType();
		String typeName = certificationType.getName();
		String email = certification.getEmail();

		return typeName + ":" + email;
	}
}
