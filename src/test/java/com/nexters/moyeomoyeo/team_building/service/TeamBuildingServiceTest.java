package com.nexters.moyeomoyeo.team_building.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nexters.moyeomoyeo.MoyeoMoyeoTest;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.TeamBuildingRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.TeamRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse;
import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MoyeoMoyeoTest
class TeamBuildingServiceTest {

	@Autowired
	private TeamBuildingService teamBuildingService;

	@Autowired
	private TeamBuildingAdminService adminService;


	@Test
	void createTeamBuilding() {
		final var saveResponse = saveTeamBuildingWithTeams();
		final TeamBuildingResponse findResponse = teamBuildingService.findTeamBuildingAndTeams(
			saveResponse.getTeamBuildingInfo().getTeamBuildingUrl());

		// team building
		assertEquals(saveResponse.getTeamBuildingInfo().getTeamBuildingUrl(),
			findResponse.getTeamBuildingInfo().getTeamBuildingUrl());
		assertEquals(RoundStatus.FIRST_ROUND, findResponse.getTeamBuildingInfo().getRoundStatus());

		//team
		assertEquals(saveResponse.getTeamInfoList().get(0).getTeamName(),
			findResponse.getTeamInfoList().get(0).getTeamName());
		assertEquals(saveResponse.getTeamInfoList().get(1).getTeamName(),
			findResponse.getTeamInfoList().get(1).getTeamName());
	}

	private TeamBuildingResponse saveTeamBuildingWithTeams() {
		return adminService.createTeamBuilding(TeamBuildingRequest.builder()
			.name("team-Building")
			.teams(List.of(
				TeamRequest.builder()
					.name("team1")
					.pmName("pm1")
					.pmPosition(Position.FRONT_END)
					.build(),
				TeamRequest.builder()
					.name("team2")
					.pmName("pm2")
					.pmPosition(Position.BACK_END)
					.build()))
			.build());
	}
}
