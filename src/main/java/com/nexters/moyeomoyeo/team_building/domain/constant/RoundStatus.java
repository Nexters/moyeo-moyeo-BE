package com.nexters.moyeomoyeo.team_building.domain.constant;

import lombok.Getter;

public enum RoundStatus {
	COMPLETE(6, null),
	ADJUSTED_ROUND(5, COMPLETE),
	FORTH_ROUND(4, ADJUSTED_ROUND),
	THIRD_ROUND(3, FORTH_ROUND),
	SECOND_ROUND(2, THIRD_ROUND),
	FIRST_ROUND(1, SECOND_ROUND),
	START(0, FIRST_ROUND)
	;

	@Getter
	private final int order;

	@Getter
	private final RoundStatus nextStatus;

	RoundStatus(int order, RoundStatus nextStatus) {
		this.order = order;
		this.nextStatus = nextStatus;
	}
}
