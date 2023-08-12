package com.nexters.moyeomoyeo.team_building.controller.dto.response;

import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	public static boolean isSelectDone(RoundStatus roundStatus, RoundStatus teamStatus) {
		if (RoundStatus.COMPLETE == roundStatus) {
			return true;
		}

		return roundStatus.getNextStatus() == teamStatus;
	}

	public static TeamInfo makeTeamInfo(Team team) {
		return TeamInfo.builder()
			.uuid(team.getUuid())
			.teamName(team.getName())
			.isSelectDone(isSelectDone(team.getTeamBuilding().getRoundStatus(), team.getRoundStatus()))
			.pmName(team.getPmName())
			.pmPosition(team.getPmPosition())
			.build();
	}
}
