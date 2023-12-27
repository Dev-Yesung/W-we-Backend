package wave.domain.mail;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Getter
@RequiredArgsConstructor
public enum CertificationType {
	SIGNUP("signup", "WAVE 회원가입을 위한 인증코드 발송입니다.", 180L),
	LOGIN("login", "WAVE 로그인을 위한 인증코드 발송입니다.", 300L);

	private final String typeName;
	private final String mailTitle;
	private final long timeLimit;

	public static CertificationType getCertificationType(String typeName) {
		return Arrays.stream(values())
			.filter(certificationType -> certificationType.getTypeName().equals(typeName))
			.findAny()
			.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CERTIFICATION_MAIL_TYPE));
	}
}
