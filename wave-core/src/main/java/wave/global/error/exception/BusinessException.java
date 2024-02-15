package wave.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wave.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

}
