package com.nexters.moyeomoyeo.team_building.service;


import static com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse.TeamBuildingInfo.makeTeamBuildingInfo;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamInfo;
import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import com.nexters.moyeomoyeo.team_building.domain.repository.TeamBuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamBuildingService {

	private final TeamBuildingRepository teamBuildingRepository;

	@Transactional(readOnly = true)
	public TeamBuildingResponse findTeamBuildingAndTeams(String roomUuid) {
		final TeamBuilding teamBuilding = findByUuid(roomUuid);

		return TeamBuildingResponse.builder()
			.teamBuildingInfo(makeTeamBuildingInfo(teamBuilding))
			.teamInfoList(teamBuilding.getTeams().stream().map(TeamInfo::makeTeamInfo).toList())
			.build();
	}

	public TeamBuilding findByUuid(String teamBuildingUuid) {
		return teamBuildingRepository.findByUuid(teamBuildingUuid)
			.orElseThrow(ExceptionInfo.INVALID_TEAM_BUILDING_UUID::exception);
	}

	public TeamBuilding save(TeamBuilding teamBuilding) {
		return teamBuildingRepository.save(teamBuilding);
	}
}
