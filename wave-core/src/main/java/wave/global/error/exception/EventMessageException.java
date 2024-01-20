package wave.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wave.global.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public class EventMessageException extends RuntimeException {

	private final ErrorCode errorCode;

	public EventMessageException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}
}
