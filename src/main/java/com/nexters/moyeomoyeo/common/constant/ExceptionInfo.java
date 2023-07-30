package com.nexters.moyeomoyeo.common.constant;

import com.nexters.moyeomoyeo.common.exception.vo.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionInfo {
	UNEXPECTED_EXCEPTION("알 수 없는 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	BAD_REQUEST("요청 값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
	INVALID_ROOM_UUID("방을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
	INVALID_TEAM_UUID("팀을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
	INVALID_SIZE_USER_CHOICE_PICK("선택한 지망이 부족합니다.", HttpStatus.BAD_REQUEST),
	EMPTY_USER_CHOICE_PICK("선택한 지망이 없습니다.", HttpStatus.BAD_REQUEST),
	INVALID_USER_CHOICE_ORDER("팀 지망은 반드시 숫자여야 합니다.", HttpStatus.BAD_REQUEST),
	BAD_REQUEST_FOR_USER_PICK("해당 유저를 선택할 수 없습니다.", HttpStatus.BAD_REQUEST),
	ALREADY_JOINED_USER("다른 팀에 배정되어 있는 유저입니다.", HttpStatus.BAD_REQUEST),
	COMPLETED_TEAM_BUILDING("이미 종료된 팀빌딩입니다.", HttpStatus.BAD_REQUEST);

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
