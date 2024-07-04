package com.nexters.moyeomoyeo.team_building.domain.constant;

import java.util.List;
import lombok.Getter;

public enum RoundStatus {
	COMPLETE(6, null),
	ADJUSTED_ROUND(5, COMPLETE),
	FOURTH_ROUND(4, ADJUSTED_ROUND),
	THIRD_ROUND(3, FOURTH_ROUND),
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

	public static boolean isPickUserPossible(RoundStatus roundStatus) {
		return List.of(FIRST_ROUND, SECOND_ROUND, THIRD_ROUND, FOURTH_ROUND).contains(roundStatus);
	}
}
