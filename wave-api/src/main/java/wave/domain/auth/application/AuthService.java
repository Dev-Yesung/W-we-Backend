package wave.domain.auth.application;

import static wave.domain.user.domain.Role.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.auth.dto.AccessToken;
import wave.domain.auth.dto.RefreshToken;
import wave.domain.auth.dto.request.CheckCertificationCodeRequest;
import wave.domain.auth.dto.request.SignupRequest;
import wave.domain.auth.dto.response.SignupResponse;
import wave.domain.auth.infra.UserCertificationRepository;
import wave.domain.mail.CertificationType;
import wave.domain.user.domain.Profile;
import wave.domain.user.domain.User;
import wave.domain.user.infra.UserRepository;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
	private final UserRepository userRepository;
	private final UserCertificationRepository userCertificationRepository;
	private final JwtCreator jwtCreator;

	@Transactional(readOnly = true)
	public void checkDuplicateEmail(String email) {
		userRepository.findByEmail(email)
			.ifPresent(user -> {
				throw new BusinessException(ErrorCode.ALREADY_EXIST_EMAIL);
			});
	}

	@Transactional(readOnly = true)
	public void checkCertificationCode(CheckCertificationCodeRequest request) {
		String email = request.email();
		CertificationType certificationType = getCertificationType(request);
		boolean isExist = userCertificationRepository
			.existCertificationCodeByEmailAndType(certificationType, email);

		if (!isExist) {
			throw new BusinessException(ErrorCode.NOT_FOUND_CERTIFICATION_CODE);
		}
	}

	public SignupResponse signup(SignupRequest request) {
		String foundCertificationCode = getCertificationCode(request);
		validateCertificationCode(request, foundCertificationCode);
		User newUser = getNewUser(request);
		User savedUser = userRepository.save(newUser);
		Token tokens = createTokens(savedUser);

		return getSignupResponse(newUser, tokens);
	}

	private CertificationType getCertificationType(CheckCertificationCodeRequest request) {
		String type = request.type();

		return CertificationType.getCertificationType(type);
	}

	private String getCertificationCode(SignupRequest request) {
		String email = request.email();
		return userCertificationRepository
			.getAndDeleteCertificationCodeByEmailAndType(CertificationType.SIGNUP, email)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CERTIFICATION_CODE));
	}

	private void validateCertificationCode(SignupRequest request, String foundCertificationCode) {
		String certificationCode = request.certificationCode();
		if (!certificationCode.equals(foundCertificationCode)) {
			throw new BusinessException(ErrorCode.INVALID_CERTIFICATION_NUMBER);
		}
	}

	private User getNewUser(SignupRequest request) {
		String email = request.email();
		String nickname = request.nickname();
		Profile newProfile = new Profile("", "");

		return new User(email, nickname, newProfile, USER);
	}

	private Token createTokens(User user) {
		AccessToken accessToken = jwtCreator.createAccessToken(user);
		RefreshToken refreshToken = jwtCreator.createRefreshToken(user);

		return new Token(accessToken, refreshToken);
	}

	private SignupResponse getSignupResponse(User newUser, Token tokens) {
		String email = newUser.getEmail();
		AccessToken accessToken = tokens.accessToken();
		String rawAccessToken = accessToken.rawAccessToken();
		RefreshToken refreshToken = tokens.refreshToken();
		String rawRefreshToken = refreshToken.rawRefreshToken();

		return new SignupResponse(email, rawAccessToken, rawRefreshToken);
	}

	private record Token(
		AccessToken accessToken,
		RefreshToken refreshToken
	) {
	}
}
