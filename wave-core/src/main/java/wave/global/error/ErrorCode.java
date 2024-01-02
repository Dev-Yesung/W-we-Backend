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

	INVALID_CERTIFICATION_MAIL_TYPE("-M001", "잘못된 인증메일요청 타입입니다."),

	INVALID_UPLOAD_PATH("-F001", "잘못된 음원 파일 업로드 경로입니다."),
	INVALID_FILE_NAME("-F002", "올바르지 않은 음원 파일 이름입니다."),
	UNABLE_TO_MAKE_DIRECTORY("-F003", "음원 파일 업로드 경로를 생성할 수 없습니다."),
	EXCEED_MAX_FILE_SIZE("-F004", "음원 파일의 최대 업로드 사이즈를 초과했습니다."),
	INVALID_FILE_EXTENSION("-F005", "지원하지 않는 음원 파일 형식입니다."),
	UNABLE_TO_UPLOAD("-F006", "음원 파일을 업로드 하던 중 오류가 발생했습니다."),

	INVALID_URL("-P001", "올바르지 않은 URL입니다."),
	;

	private final String code;
	private final String message;
}
