package com.nexters.moyeomoyeo.common.constant;

import com.nexters.moyeomoyeo.common.exception.vo.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionInfo {
	UNEXPECTED_EXCEPTION("알 수 없는 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

	BAD_REQUEST("요청 값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);

	private final String message;
	private final HttpStatus status;

	ExceptionInfo(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	public CustomException exception() {
		return CustomException.builder()
			.message(this.message)
			.status(this.status)
			.build();
	}

	public CustomException exception(Throwable cause) {
		return CustomException.builder()
			.cause(cause)
			.message(this.message)
			.status(this.status)
			.build();
	}
}
