package com.nexters.moyeomoyeo.team_building.controller.dto;

import com.nexters.moyeomoyeo.team_building.domain.constant.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TeamInfo {

	private String uuid;
	private String teamName;
	private String pmName;
	private Position pmPosition;
	private boolean isSelectDone;

	public static boolean isSelectDone(RoundStatus roomStatus, RoundStatus teamStatus) {
		if (RoundStatus.COMPLETE == roomStatus) {
			return true;
		}

		return roomStatus.getNextStatus() == teamStatus;
	}
}
