package com.nexters.moyeomoyeo.team_building.controller.dto;

import com.nexters.moyeomoyeo.team_building.domain.constant.*;
import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TeamInfo {
	@Schema(description = "팀 고유값")
	private String uuid;
	@Schema(description = "팀 명")
	private String teamName;
	@Schema(description = "PM 이름")
	private String pmName;
	@Schema(description = "PM 포지션")
	private Position pmPosition;
	@Schema(description = "팀원 선택이 완료되었는지 상태")
	private boolean isSelectDone;

	public static boolean isSelectDone(RoundStatus roomStatus, RoundStatus teamStatus) {
		if (RoundStatus.COMPLETE == roomStatus) {
			return true;
		}

		return roomStatus.getNextStatus() == teamStatus;
	}
}
