package com.nexters.moyeomoyeo.common.exception.handler;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.common.exception.dto.ExceptionResponse;
import com.nexters.moyeomoyeo.common.exception.vo.CustomException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponse> handleCustomException(CustomException cause) {
		log.error("customException", cause);
		return ExceptionResponse.of(cause);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException cause) {
		final CustomException exception = CustomException.builder()
			.status(HttpStatus.BAD_REQUEST)
			.message(ExceptionInfo.BAD_REQUEST.getMessage())
			.params(cause.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage)))
			.build();
		log.error("methodArgumentNotValidException", cause);
		return ExceptionResponse.of(exception);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException cause) {
		final CustomException exception = CustomException.builder()
			.status(HttpStatus.BAD_REQUEST)
			.message(ExceptionInfo.BAD_REQUEST.getMessage())
			.build();
		log.error("methodArgumentTypeMismatchException", cause);
		return ExceptionResponse.of(exception);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException cause) {
		final CustomException exception = CustomException.builder()
			.status(HttpStatus.BAD_REQUEST)
			.message(ExceptionInfo.BAD_REQUEST.getMessage())
			.param("method", cause.getMethod())
			.build();
		log.error("httpRequestMethodNotSupportedException", cause);
		return ExceptionResponse.of(exception);
	}

	@ExceptionHandler({RuntimeException.class, Exception.class})
	public ResponseEntity<ExceptionResponse> handleExtraException(Exception cause) {
		final CustomException exception = CustomException.builder()
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.message(ExceptionInfo.UNEXPECTED_EXCEPTION.getMessage())
			.build();
		log.error("unknownException", cause);
		return ExceptionResponse.of(exception);
	}
}
