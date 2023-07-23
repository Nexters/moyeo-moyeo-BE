package com.nexters.moyeomoyeo.common.exception.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

	private final HttpStatus status;
	private final String message;
	@JsonInclude(Include.NON_NULL)
	private final Map<String, Object> params;

	@Builder
	public CustomException(String message, HttpStatus status, @Singular Map<String, Object> params, Throwable cause) {
		super(message, cause);
		this.status = status;
		this.message = message;
		this.params = params;
	}

}
