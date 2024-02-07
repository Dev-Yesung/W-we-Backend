package wave.domain.account.application;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.global.common.UseCase;
import wave.domain.account.application.jwt.JwtUtils;
import wave.domain.account.domain.port.out.LoadAccountPort;
import wave.domain.account.domain.port.out.PublishAccountEventPort;
import wave.domain.account.domain.port.out.SubscribeAccountEventPort;
import wave.domain.account.domain.port.out.UpdateAccountPort;
import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.jwt.AccessToken;
import wave.domain.account.domain.vo.jwt.RefreshToken;
import wave.domain.account.dto.request.CertificationVerifyRequest;
import wave.domain.account.dto.request.NewCertificationRequest;
import wave.domain.account.dto.request.SignupRequest;
import wave.domain.account.dto.response.AccountResponse;
import wave.domain.account.dto.response.CertificationResponse;
import wave.domain.account.domain.vo.CertificationType;

@RequiredArgsConstructor
@Transactional
@UseCase
public class AccountService {

	private final UpdateAccountPort updateAccountPort;
	private final LoadAccountPort loadAccountPort;
	private final PublishAccountEventPort publishAccountEventPort;
	private final SubscribeAccountEventPort subscribeAccountEventPort;

	private final JwtUtils jwtUtils;

	@Transactional(readOnly = true)
	public void checkDuplicateEmail(String email) {
		loadAccountPort.checkDuplicateEmail(email);
	}

	@Transactional(readOnly = true)
	public CertificationResponse requestCertificationCode(NewCertificationRequest request) {
		Certification certification = NewCertificationRequest.of(request);
		publishAccountEventPort.publishCertificationEvent(certification);
		int ttl = updateAccountPort.cacheCertificationCode(certification);

		return new CertificationResponse(ttl);
	}

	public void verifyCertificationCode(CertificationVerifyRequest request) {
		Certification certification = CertificationVerifyRequest.of(request);
		validateCertificationCode(certification);
		updateAccountPort.removeCertificationCode(certification);
	}

	public AccountResponse signup(SignupRequest request) {
		isExistedCertificationCode(request);
		User savedUser = saveUserAndPublishAccountEvent(request);

		return getAccountResponse(savedUser);
	}

	private void validateCertificationCode(Certification certification) {
		String certificationCode = loadAccountPort.getCertificationCode(certification);
		certification.validateCode(certificationCode);
	}

	private void isExistedCertificationCode(SignupRequest request) {
		String email = request.email();
		loadAccountPort.existCertificationCode(CertificationType.SIGNUP, email);
	}

	private User saveUserAndPublishAccountEvent(SignupRequest request) {
		User newUser = SignupRequest.of(request);
		User savedUser = updateAccountPort.saveAccount(newUser);
		publishAccountEventPort.publishNewAccountEvent(savedUser);

		return savedUser;
	}

	private AccountResponse getAccountResponse(User savedUser) {
		AccessToken accessToken = jwtUtils.createAccessToken(savedUser);
		RefreshToken refreshToken = jwtUtils.createRefreshToken(savedUser);

		return AccountResponse.from(savedUser, accessToken, refreshToken);
	}

}
