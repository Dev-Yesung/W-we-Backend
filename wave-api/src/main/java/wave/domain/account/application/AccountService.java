package wave.domain.account.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.AccountLoadPort;
import wave.domain.account.domain.AccountUpdatePort;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.dto.AccessToken;
import wave.domain.account.dto.RefreshToken;
import wave.domain.account.dto.request.CertificationRequest;
import wave.domain.account.dto.request.CertificationVerifyRequest;
import wave.domain.account.dto.request.SignupRequest;
import wave.domain.account.dto.response.AccountResponse;
import wave.domain.account.dto.response.CertificationResponse;
import wave.domain.account.application.jwt.JwtFactory;
import wave.domain.account.domain.CertificationCodeFactory;
import wave.domain.mail.CertificationType;
import wave.domain.account.domain.vo.Profile;
import wave.domain.account.domain.vo.Role;
import wave.domain.account.domain.entity.User;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

	private final AccountUpdatePort accountUpdatePort;
	private final AccountLoadPort accountLoadPort;

	private final CertificationCodeFactory certificationCodeFactory;
	private final JwtFactory jwtFactory;

	@Transactional(readOnly = true)
	public void checkDuplicateEmail(String email) {
		accountLoadPort.checkDuplicateEmail(email);
	}

	@Transactional(readOnly = true)
	public CertificationResponse requestCertificationCode(CertificationRequest request) {
		Certification certification = createCertification(request);
		accountUpdatePort.publishCertificationEvent(certification);
		int ttl = accountUpdatePort.cacheCertificationCode(certification);

		return new CertificationResponse(ttl);
	}

	@Transactional(readOnly = true)
	public void verifyCertificationCode(CertificationVerifyRequest request) {
		Certification certification = getCertification(request);
		String certificationCode = accountLoadPort.getCertificationCode(certification);
		certification.validateCode(certificationCode);
		accountUpdatePort.removeCertificationCode(certification);
	}

	public AccountResponse signup(SignupRequest request) {
		String email = request.email();
		boolean isExist = accountLoadPort.isExistCertificationCode(CertificationType.SIGNUP, email);
		if (isExist) {
			throw new BusinessException(ErrorCode.NOT_VERIFIED_CERTIFICATION_CODE);
		}

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

	private Certification getCertification(CertificationVerifyRequest request) {
		String typeName = request.typeName();
		CertificationType certificationType = CertificationType.getCertificationType(typeName);
		String email = request.email();
		String certificationCode = request.certificationCode();

		return new Certification(certificationType, email, certificationCode);
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
}
