package wave.domain.account.domain.vo;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@Getter
@RequiredArgsConstructor
public enum CertificationType {
	SIGNUP("회원가입 인증코드","signup_certification", 300),
	LOGIN("로그인 인증코드","login_certification", 180);

	private final String name;
	private final String eventName;
	private final int ttl;

	public static CertificationType getCertificationType(String typeName) {
		return Arrays.stream(values())
			.filter(certificationType -> certificationType.getEventName().equals(typeName))
			.findAny()
			.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_MAIL_TYPE));
	}
}
