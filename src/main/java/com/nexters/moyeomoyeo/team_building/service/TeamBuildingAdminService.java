package com.nexters.moyeomoyeo.team_building.service;


import static com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse.TeamBuildingInfo.makeTeamBuildingInfo;
import static com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo.makeUserInfo;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.notification.service.NotificationService;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.TeamBuildingRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.TeamRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamInfo;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamBuildingAdminService {

	private final TeamBuildingService teamBuildingService;
	private final UserService userService;
	private final NotificationService notificationService;

	@Transactional
	public UserInfo adjustUser(String teamBuildingUuid, String userUuid, String teamUuid) {
		final TeamBuilding teamBuilding = teamBuildingService.findByUuid(teamBuildingUuid);

		if (RoundStatus.ADJUSTED_ROUND != teamBuilding.getRoundStatus()) {
			throw ExceptionInfo.INVALID_ADJUST_REQUEST.exception();
		}

		final User user = userService.findByUuid(userUuid);
		final Team targetTeam = teamBuilding.getTeams()
			.stream()
			.filter(team -> Objects.equals(team.getUuid(), teamUuid))
			.findFirst()
			.orElse(null);
		user.adjustTeam(targetTeam);
		UserInfo userInfo = makeUserInfo(user);

		notificationService.broadCast(teamBuildingUuid, "adjust-user", userInfo);
		return userInfo;
	}


	@Transactional
	public void finishTeamBuilding(String uuid) {
		final TeamBuilding teamBuilding = teamBuildingService.findByUuid(uuid);

		if (RoundStatus.ADJUSTED_ROUND != teamBuilding.getRoundStatus()) {
			throw ExceptionInfo.INVALID_FINISH_REQUEST.exception();
		}

		teamBuilding.nextRound();
		notificationService.broadCast(teamBuilding.getUuid(), "finish-team-building", teamBuilding.getRoundStatus());
	}


	@Transactional
	public TeamBuildingResponse createTeamBuilding(TeamBuildingRequest teamBuildingRequest) {
		final TeamBuilding teamBuilding = TeamBuilding.builder()
			.name(teamBuildingRequest.getName())
			.build();

		final List<Team> teams = teamBuildingRequest.getTeams()
			.stream()
			.map(TeamRequest::toEntity)
			.toList();

		for (final Team team : teams) {
			team.addTeamBuilding(teamBuilding);
		}

		final TeamBuilding savedTeamBuilding = teamBuildingService.save(teamBuilding);

		return TeamBuildingResponse.builder()
			.teamBuildingInfo(makeTeamBuildingInfo(savedTeamBuilding))
			.teamInfoList(savedTeamBuilding.getTeams().stream().map(TeamInfo::makeTeamInfo).toList())
			.build();
	}

}
