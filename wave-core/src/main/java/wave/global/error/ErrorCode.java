package wave.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	INVALID_METHOD_ARGUMENT("", ""),

	INVALID_FILE_NAME("-F001", "유효하지 않은 파일이름 입니다."),
	INVALID_FILE_EXTENSION("-F002", "유효하지 않은 파일확장자 입니다."),
	INVALID_FILE_SIZE("-F003", "유효하지 않은 파일의 크기입니다."),
	INVALID_FILE_PATH("-F004", "경로에 공백이나 허용되지 않는 특수문자가 올 수 없습니다."),
	UNABLE_TO_MAKE_DIRECTORY("-F005", "경로를 생성할 수 없습니다."),
	UNABLE_TO_UPLOAD_FILE("-F006", "파일을 업로드 중 오류가 발생했습니다."),
	NOT_FOUND_FILE("-F007", "파일을 찾을 수 없습니다."),
	NOT_FOUND_FILE_DIRECTORY("-F008", "파일의 디렉토리를 찾을 수 없습니다."),
	UNABLE_TO_GET_FILE_DATA("-F009", "파일의 데이터를 가져올 수 없습니다."),

	NOT_FOUND_USER("-U001", "유저를 찾을 수 없습니다."),
	NOT_FOUND_USER_EMAIL("-U002", "이메일 정보를 찾을 수 없습니다."),
	NOT_FOUND_USER_ROLL("-U003", "Role 정보를 찾을 수 없습니다."),
	ALREADY_EXIST_USER_EMAIL("-UOO4", "이미 존재하는 이메일입니다."),

	AUTHENTICATION_FAIL("-A001", "인증에 실패했습니다."),
	NOT_FOUND_CERTIFICATION_CODE("-A002", "인증코드를 찾을 수 없습니다."),
	INVALID_CERTIFICATION_CODE("-A003", "잘못된 인증코드를 입력했습니다."),
	NOT_VERIFIED_CERTIFICATION_CODE("-A004", "인증이 완료되지 않았습니다."),
	EXPIRED_JWT("-A005", "토큰이 만료됐습니다."),
	AUTH_METHOD_NOT_SUPPORTED("-A006", "잘못된 방식의 인증입니다."),

	INVALID_MAIL_TYPE("-M001", "잘못된 메일 타입입니다."),
	FAIL_TO_SEND_EMAIL("-M002", "메일을 전송하는데 실패했습니다."),

	INVALID_MUSIC_FILE_NAME("-MF002", "올바르지 않은 음원 파일 이름입니다."),
	EXCEED_MAX_MUSIC_FILE_SIZE("-MF004", "음원 파일의 최대 업로드 사이즈를 초과했습니다."),
	INVALID_MUSIC_FILE_PATH("-MF007", "입력된 음원 파일의 경로가 올바르지 않습니다."),
	INVALID_MUSIC_URL("-MF011", "음원의 URL이 올바르지 않습니다."),
	INVALID_IMAGE_URL("-MF012", "이미지의 URL이 올바르지 않습니다."),

	INVALID_POST_URL("-P001", "올바르지 않은 포스트 조회입니다."),
	NOT_FOUND_POST("-P002", "포스트를 찾을 수 없습니다."),
	NO_AUTHORITY_TO_POST("-P003", "포스트에 권한이 없습니다."),

	INVALID_PARTIAL_RANGE("-SM001", "스트리밍 요청범위가 올바르지 않습니다."),
	UNABLE_TO_OUTPUT("-SM002", "스트리밍할 파일을 가져올 수 없습니다."),

	NOT_FOUND_COMMENT("-C001", "댓글을 찾을 수 없습니다."),
	NOT_INCLUDED_COMMENT_TO_POST("-C002", "해당 댓글과 일치하지 않는 포스트입니다."),
	NO_AUTHORITY_TO_COMMENT("-C003", "해당 댓글에 권한이 없습니다."),

	INVALID_MESSAGE_CASTING("-EVT001", "메시지의 객체변환이 잘못되었습니다."),

	UNABLE_TO_SEND_NOTIFICATION("-NM001", "알림 메시지를 보내는데 실패했습니다."),
	UNABLE_TO_SERIALIZE_NOTIFICATION("-NM002", "알림 메시지를 변환하는데 실패했습니다."),
	INVALID_CONNECTION_FOR_NOTIFICATION("-NM003", "클라이언트와의 연결에 실패했습니다."),
	NOT_FOUND_NOTIFICATION("-NM004", "알림 메시지를 찾을 수 없습니다."),

	NO_AUTHORITY_TO_LIKE("-L001", "해당 좋아요에 권한이 없습니다.");

	private final String code;
	private final String message;
}
