package wave.domain.account.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.account.application.jwt.JwtUtils;
import wave.domain.account.domain.AccountLoadPort;
import wave.domain.account.domain.AccountUpdatePort;
import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.jwt.AccessToken;
import wave.domain.account.domain.vo.jwt.RefreshToken;
import wave.domain.account.dto.request.CertificationVerifyRequest;
import wave.domain.account.dto.request.NewCertificationRequest;
import wave.domain.account.dto.request.SignupRequest;
import wave.domain.account.dto.response.AccountResponse;
import wave.domain.account.dto.response.CertificationResponse;
import wave.domain.mail.CertificationType;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

	private final AccountUpdatePort accountUpdatePort;
	private final AccountLoadPort accountLoadPort;

	private final JwtUtils jwtUtils;

	@Transactional(readOnly = true)
	public void checkDuplicateEmail(String email) {
		accountLoadPort.checkDuplicateEmail(email);
	}

	@Transactional(readOnly = true)
	public CertificationResponse requestCertificationCode(NewCertificationRequest request) {
		Certification certification = NewCertificationRequest.of(request);
		accountUpdatePort.publishCertificationEvent(certification);
		int ttl = accountUpdatePort.cacheCertificationCode(certification);

		return new CertificationResponse(ttl);
	}

	public void verifyCertificationCode(CertificationVerifyRequest request) {
		Certification certification = CertificationVerifyRequest.of(request);
		validateCertificationCode(certification);
		accountUpdatePort.removeCertificationCode(certification);
	}

	public AccountResponse signup(SignupRequest request) {
		isExistedCertificationCode(request);
		User savedUser = saveUserAndPublishAccountEvent(request);

		return getAccountResponse(savedUser);
	}

	private void validateCertificationCode(Certification certification) {
		String certificationCode = accountLoadPort.getCertificationCode(certification);
		certification.validateCode(certificationCode);
	}

	private void isExistedCertificationCode(SignupRequest request) {
		String email = request.email();
		accountLoadPort.existCertificationCode(CertificationType.SIGNUP, email);
	}

	private User saveUserAndPublishAccountEvent(SignupRequest request) {
		User newUser = SignupRequest.of(request);
		User savedUser = accountUpdatePort.saveAccount(newUser);
		accountUpdatePort.publishNewAccountEvent(savedUser);

		return savedUser;
	}

	private AccountResponse getAccountResponse(User savedUser) {
		AccessToken accessToken = jwtUtils.createAccessToken(savedUser);
		RefreshToken refreshToken = jwtUtils.createRefreshToken(savedUser);

		return AccountResponse.from(savedUser, accessToken, refreshToken);
	}
}
