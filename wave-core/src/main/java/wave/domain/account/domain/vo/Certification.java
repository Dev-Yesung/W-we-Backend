package wave.domain.account.domain.vo;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Certification that = (Certification)o;
		return type == that.type && Objects.equals(email, that.email) && Objects.equals(randomCode,
			that.randomCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, email, randomCode);
	}
}
