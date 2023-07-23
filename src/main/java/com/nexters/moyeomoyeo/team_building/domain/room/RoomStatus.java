package com.nexters.moyeomoyeo.team_building.domain.room;

public enum RoomStatus {
	FIRST_ROUND(1),
	SECOND_ROUND(2),
	THIRD_ROUND(3),
	FORTH_ROUND(4),
	COMPLETE(5),
	;

	RoomStatus(int weight) {
		this.weight = weight;
	}

	private int weight;

	public boolean isFinishRound(int order) {
		return this.weight > order;
	}
}
