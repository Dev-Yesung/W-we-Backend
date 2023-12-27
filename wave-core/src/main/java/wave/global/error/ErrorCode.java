package wave.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	INVALID_METHOD_ARGUMENT("", ""),

	NOT_FOUND_USER("-U001", "유저를 찾을 수 없습니다."),
	NOT_FOUND_EMAIL("-U002", "이메일 정보를 찾을 수 없습니다."),
	NOT_FOUND_ROLL("-U003", "Role 정보를 찾을 수 없습니다."),
	ALREADY_EXIST_EMAIL("-UOO4", "이미 존재하는 이메일입니다."),

	AUTHENTICATION_FAIL("-A001", "인증에 실패했습니다."),
	NOT_FOUND_CERTIFICATION_CODE("-A002", "인증코드를 찾을 수 없습니다."),
	INVALID_CERTIFICATION_NUMBER("-A003", "잘못된 인증코드를 입력했습니다."),
	EXPIRED_JWT("-A004", "토큰이 만료됐습니다."),
	AUTH_METHOD_NOT_SUPPORTED("-A005", "잘못된 방식의 인증입니다."),

	INVALID_CERTIFICATION_MAIL_TYPE("-M001", "잘못된 인증메일요청 타입입니다.");

	private final String code;
	private final String message;
}
