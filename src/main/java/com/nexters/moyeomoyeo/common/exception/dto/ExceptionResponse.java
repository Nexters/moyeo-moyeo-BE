package com.nexters.moyeomoyeo.common.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nexters.moyeomoyeo.common.exception.vo.CustomException;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ExceptionResponse {

	private final String message;
	@JsonInclude(Include.NON_NULL)
	private final Map<String, Object> params;


	public static ResponseEntity<ExceptionResponse> of(final CustomException exception) {
		return ResponseEntity.status(exception.getStatus().value())
			.body(ExceptionResponse.builder()
				.message(exception.getMessage())
				.params(exception.getParams())
				.build());
	}
}
