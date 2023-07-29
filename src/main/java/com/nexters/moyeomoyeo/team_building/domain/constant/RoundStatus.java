package com.nexters.moyeomoyeo.team_building.domain.constant;

import lombok.Getter;

public enum RoundStatus {
	COMPLETE(6, null),
	ADJUSTED_ROUND(5, COMPLETE),
	FORTH_ROUND(4, ADJUSTED_ROUND),
	THIRD_ROUND(3, FORTH_ROUND),
	SECOND_ROUND(2, THIRD_ROUND),
	FIRST_ROUND(1, SECOND_ROUND),
	;

	@Getter
	final private int weight;

	@Getter
	final private RoundStatus nextStatus;

	RoundStatus(int weight, RoundStatus nextStatus) {
		this.weight = weight;
		this.nextStatus = nextStatus;
	}

	public boolean isFinishRound(int order) {
		return this.weight > order;
	}
}
