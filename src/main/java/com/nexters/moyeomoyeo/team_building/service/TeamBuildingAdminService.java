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
import com.nexters.moyeomoyeo.team_building.domain.repository.TeamBuildingRepository;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamBuildingAdminService {

	private final UserService userService;
	private final NotificationService notificationService;
	private final TeamBuildingRepository teamBuildingRepository;

	@Nullable
	private static Team findTeam(String teamUuid, TeamBuilding teamBuilding) {
		return teamBuilding.getTeams()
			.stream()
			.filter(team -> Objects.equals(team.getUuid(), teamUuid))
			.findFirst()
			.orElse(null);
	}

	@Transactional
	public UserInfo adjustUser(String teamBuildingUuid, String userUuid, String teamUuid) {
		final TeamBuilding teamBuilding = findByUuid(teamBuildingUuid);

		if (RoundStatus.ADJUSTED_ROUND != teamBuilding.getRoundStatus()) {
			throw ExceptionInfo.INVALID_ADJUST_REQUEST.exception();
		}

		final User user = userService.findByUuid(userUuid);
		final Team team = findTeam(teamUuid, teamBuilding);

		user.adjustTeam(team);
		user.updateSelectedRound(Objects.isNull(team) ? null : RoundStatus.ADJUSTED_ROUND);

		UserInfo userInfo = makeUserInfo(user);
		notificationService.broadcast(teamBuildingUuid, "adjust-user", userInfo);
		return userInfo;
	}


	@Transactional
	public void deleteTeamBuildingUser(String uuid, String userUuid) {
		final TeamBuilding teamBuilding = findByUuid(uuid);

		if (RoundStatus.START != teamBuilding.getRoundStatus()) {
			throw ExceptionInfo.INVALID_DELETE_REQUEST.exception();
		}
		userService.deleteUser(uuid, userUuid);
	}

	@Transactional
	public void finishTeamBuilding(String uuid) {
		final TeamBuilding teamBuilding = findByUuid(uuid);

		if (RoundStatus.ADJUSTED_ROUND != teamBuilding.getRoundStatus()) {
			throw ExceptionInfo.INVALID_FINISH_REQUEST.exception();
		}

		teamBuilding.nextRound();
		notificationService.broadcast(teamBuilding.getUuid(), "finish-team-building", teamBuilding.getRoundStatus());
	}


	@Transactional
	public TeamBuildingResponse createTeamBuilding(TeamBuildingRequest teamBuildingRequest) {
		final List<Team> teams = teamBuildingRequest.getTeams()
			.stream()
			.map(TeamRequest::toEntity)
			.toList();

		final TeamBuilding teamBuilding = teamBuildingRepository.save(TeamBuilding.builder()
			.name(teamBuildingRequest.getName())
			.teams(teams)
			.build());

		for (final Team team : teams) {
			team.addTeamBuilding(teamBuilding);
		}

		return TeamBuildingResponse.builder()
			.teamBuildingInfo(makeTeamBuildingInfo(teamBuilding))
			.teamInfoList(teamBuilding.getTeams().stream().map(TeamInfo::makeTeamInfo).toList())
			.build();
	}

	private TeamBuilding findByUuid(String teamBuildingUuid) {
		return teamBuildingRepository.findByUuid(teamBuildingUuid)
			.orElseThrow(ExceptionInfo.INVALID_TEAM_BUILDING_UUID::exception);
	}
}
