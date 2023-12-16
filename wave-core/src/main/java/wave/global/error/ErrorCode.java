package wave.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	INVALID_METHOD_ARGUMENT("", "");

	private final String code;
	private final String message;
}
