package wave.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wave.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class FileException extends RuntimeException {

	private final ErrorCode errorCode;

	public FileException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

}
