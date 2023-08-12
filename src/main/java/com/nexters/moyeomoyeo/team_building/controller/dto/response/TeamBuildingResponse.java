package com.nexters.moyeomoyeo.team_building.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(Include.NON_NULL)
public class TeamBuildingResponse {

	@Schema(description = "팀 빌딩 정보")
	private TeamBuildingInfo teamBuildingInfo;
	@Schema(description = "팀빌딩에 참여하는 팀 정보")
	private List<TeamInfo> teamInfoList;
	@Schema(description = "팀빌딩에 참여하는 회원 정보")
	private List<UserInfo> userInfoList;

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Builder
	public static class TeamBuildingInfo {

		@Schema(description = "팀 빌딩 방 고유값")
		private String teamBuildingUrl;
		@Schema(description = "팀 빌딩 이름")
		private String teamBuildingName;
		@Schema(description = "팀 빌딩 진행 상태")
		private RoundStatus roundStatus;

		public static TeamBuildingInfo makeTeamBuildingInfo(TeamBuilding teamBuilding) {
			return TeamBuildingInfo.builder()
				.teamBuildingUrl(teamBuilding.getUuid())
				.teamBuildingName(teamBuilding.getName())
				.roundStatus(teamBuilding.getRoundStatus())
				.build();
		}
	}
}
