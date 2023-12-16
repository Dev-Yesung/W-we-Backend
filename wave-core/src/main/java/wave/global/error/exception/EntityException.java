package wave.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wave.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class EntityException extends RuntimeException {
	private final ErrorCode errorCode;
}
