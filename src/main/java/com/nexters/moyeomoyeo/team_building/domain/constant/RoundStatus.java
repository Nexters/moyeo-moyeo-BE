package com.nexters.moyeomoyeo.team_building.domain.constant;

import lombok.Getter;

public enum RoundStatus {
	FIRST_ROUND(1),
	SECOND_ROUND(2),
	THIRD_ROUND(3),
	FORTH_ROUND(4),
	ADJUSTED_ROUND(5),
	COMPLETE(6),
	;

	@Getter
	final private int weight;

	RoundStatus(int weight) {
		this.weight = weight;
	}

	public boolean isFinishRound(int order) {
		return this.weight > order;
	}
}
