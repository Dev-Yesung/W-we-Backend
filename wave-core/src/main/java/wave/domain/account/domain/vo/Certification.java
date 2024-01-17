package wave.domain.account.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wave.domain.mail.CertificationType;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Getter
@RequiredArgsConstructor
public class Certification {
	private final CertificationType type;
	private final String email;
	private final String randomCode;

	public void validateCode(String certificationCode) {
		if (!randomCode.equals(certificationCode)) {
			throw new BusinessException(ErrorCode.INVALID_CERTIFICATION_CODE);
		}
	}
}
