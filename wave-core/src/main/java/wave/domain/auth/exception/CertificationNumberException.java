package wave.domain.auth.exception;

import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

public class CertificationNumberException extends BusinessException {
	public CertificationNumberException(ErrorCode errorCode) {
		super(errorCode);
	}
}
