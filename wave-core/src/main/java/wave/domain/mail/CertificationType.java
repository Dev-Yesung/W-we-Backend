package wave.domain.mail;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Getter
@RequiredArgsConstructor
public enum CertificationType {
	SIGNUP("signup_certification", 300),
	LOGIN("login_certification", 180);

	private final String name;
	private final int ttl;

	public static CertificationType getCertificationType(String typeName) {
		return Arrays.stream(values())
			.filter(certificationType -> certificationType.getName().equals(typeName))
			.findAny()
			.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_MAIL_TYPE));
	}
}
