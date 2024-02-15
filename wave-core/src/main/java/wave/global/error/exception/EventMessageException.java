package wave.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wave.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class EventMessageException extends RuntimeException {

	private final ErrorCode errorCode;

	public EventMessageException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

}
