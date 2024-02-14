package wave.domain.account.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification {

	private CertificationType type;
	private String email;
	private String certificationCode;

	public Certification(CertificationType type, String email) {
		this(type, email, "");
	}

	public void validateCode(String certificationCode) {
		if (!this.certificationCode.equals(certificationCode)) {
			throw new BusinessException(ErrorCode.INVALID_CERTIFICATION_CODE);
		}
	}
}
