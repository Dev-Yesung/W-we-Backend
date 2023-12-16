package wave.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import wave.global.error.ErrorCode;
import wave.global.error.ErrorResponse;
import wave.global.error.exception.BusinessException;
import wave.global.error.exception.EntityException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_METHOD_ARGUMENT, e.getBindingResult());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityException.class)
	public ResponseEntity<ErrorResponse> handleEntityException(EntityException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
