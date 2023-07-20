package com.nexters.moyeomoyeo.team_building.domain.team;

import com.nexters.moyeomoyeo.team_building.domain.room.*;

public enum RoundStatus {
	FIRST_ROUND(1),
	SECOND_ROUND(2),
	THIRD_ROUND(3),
	FORTH_ROUND(4),
	COMPLETE(5),
	;

	RoundStatus(int weight) {
		this.weight = weight;
	}

	private int weight;

	public boolean canNextRound(RoomStatus roomStatus) {
		return !roomStatus.isFinishRound(this.weight);
	}
}
