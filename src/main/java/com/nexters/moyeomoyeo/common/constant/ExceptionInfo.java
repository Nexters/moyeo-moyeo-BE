package com.nexters.moyeomoyeo.common.constant;

import com.nexters.moyeomoyeo.common.exception.vo.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionInfo {
	UNEXPECTED_EXCEPTION("알 수 없는 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	BAD_REQUEST("요청 값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
	INVALID_TEAM_BUILDING_UUID("팀빌딩을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
	INVALID_TEAM_UUID("팀을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
	INVALID_USER_UUID("유저를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
	BAD_REQUEST_FOR_USER_PICK("유효하지 않은 유저 선택 요청입니다.", HttpStatus.BAD_REQUEST),
	ALREADY_JOINED_USER("다른 팀에 배정되어 있는 유저입니다.", HttpStatus.BAD_REQUEST),
	COMPLETED_TEAM_BUILDING("이미 종료된 팀빌딩입니다.", HttpStatus.BAD_REQUEST),
	INVALID_ADJUST_REQUEST("조정 라운드에서만 조정 가능합니다.", HttpStatus.BAD_REQUEST),
	INVALID_FINISH_REQUEST("조정 라운드에서만 팀빌딩을 종료할 수 있습니다.", HttpStatus.BAD_REQUEST),
	INVALID_DELETE_REQUEST("1라운드에서만 회원을 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST),
	DUPLICATED_PICK_REQUEST("이미 선택이 완료된 팀입니다.", HttpStatus.BAD_REQUEST);
	@Getter
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

	@Override
	public String toString() {
		return "ExceptionInfo{" +
			"message='" + message + '\'' +
			'}';
	}
}
