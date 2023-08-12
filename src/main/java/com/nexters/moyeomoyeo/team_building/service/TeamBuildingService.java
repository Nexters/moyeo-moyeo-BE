package com.nexters.moyeomoyeo.team_building.service;


import static com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse.TeamBuildingInfo.makeTeamBuildingInfo;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.notification.service.NotificationService;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.TeamBuildingRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.TeamRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.UserPickRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.PickUserResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamInfo;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserPickResponse;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.domain.repository.TeamBuildingRepository;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamBuildingService {

	private final NotificationService notificationService;
	private final TeamBuildingRepository teamBuildingRepository;

	private static boolean isValidUser(List<String> userUuids, List<User> pickedUsers) {
		return pickedUsers.size() == userUuids.size();
	}

	/**
	 * 선택된 유저가 현재 라운드에서 지망한 팀이 유저를 선택한 팀과 일치하는지 유효성 체크.
	 *
	 * @param team        유저를 선택한 팀
	 * @param pickedUsers 선택된 유저 목록
	 * @return 일치여부
	 */
	private static boolean isChosenTeam(Team team, List<User> pickedUsers) {
		final RoundStatus roundStatus = team.getRoundStatus();
		if (RoundStatus.ADJUSTED_ROUND == roundStatus) {
			return true;
		}

		for (final User user : pickedUsers) {
			if (!Objects.equals(user.findChoice(roundStatus.getWeight()).getTeamUuid(), team.getUuid())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 모든 팀이 선택을 완료했는지 판단.
	 *
	 * @param teams       team building 의 team 들
	 * @param roundStatus 전체 round 상태
	 * @return 완료됐으면 true
	 */
	private static boolean isAllTeamSelected(List<Team> teams, RoundStatus roundStatus) {
		for (final Team team : teams) {
			if (roundStatus == team.getRoundStatus()) {
				return false;
			}
		}

		return true;
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

		final TeamBuilding savedTeamBuilding = teamBuildingRepository.save(teamBuilding);

		return TeamBuildingResponse.builder()
			.teamBuildingInfo(makeTeamBuildingInfo(savedTeamBuilding))
			.teamInfoList(savedTeamBuilding.getTeams().stream().map(TeamInfo::makeTeamInfo).toList())
			.build();
	}


	@Transactional
	public UserPickResponse pickUsers(String uuid, String teamUuid, UserPickRequest userPickRequest) {
		final TeamBuilding teamBuilding = findWithTeamsAndUsers(uuid);

		if (RoundStatus.COMPLETE == teamBuilding.getRoundStatus()) {
			throw ExceptionInfo.COMPLETED_TEAM_BUILDING.exception();
		}

		final Team targetTeam = teamBuilding.getTeams()
			.stream()
			.filter(team -> teamUuid.equals(team.getUuid()))
			.findFirst()
			.orElseThrow(ExceptionInfo.INVALID_TEAM_UUID::exception);

		final List<String> userUuids = userPickRequest.getUserUuids();
		final List<User> pickedUsers = teamBuilding.getUsers().stream()
			.filter(user -> userUuids.contains(user.getUuid()))
			.toList();

		if (!isValidUser(userUuids, pickedUsers) || !isChosenTeam(targetTeam, pickedUsers)) {
			throw ExceptionInfo.BAD_REQUEST_FOR_USER_PICK.exception();
		}

		for (final User user : pickedUsers) {
			user.addTeam(targetTeam);
		}

		targetTeam.nextRound();
		if (isAllTeamSelected(teamBuilding.getTeams(), teamBuilding.getRoundStatus())) {
			teamBuilding.nextRound();
			notificationService.broadCast(teamBuilding.getUuid(), "change-round", teamBuilding.getRoundStatus());
		}

		PickUserResponse userResponse = PickUserResponse.builder()
			.teamName(targetTeam.getName())
			.teamUuid(teamUuid)
			.pickUserUuids(userUuids)
			.build();

		notificationService.broadCast(teamBuilding.getUuid(), "pick-user", userResponse);

		return UserPickResponse.builder()
			.userInfoList(targetTeam.getUsers().stream().map(UserInfo::makeUserInfo).toList())
			.build();
	}

	@Transactional
	public void finishTeamBuilding(String uuid) {
		final TeamBuilding teamBuilding = findWithTeamsAndUsers(uuid);

		if (RoundStatus.ADJUSTED_ROUND != teamBuilding.getRoundStatus()) {
			throw ExceptionInfo.INVALID_FINISH_REQUEST.exception();
		}

		teamBuilding.nextRound();
	}

	@Transactional(readOnly = true)
	public TeamBuildingResponse findTeamBuildingAndTeams(String roomUuid) {
		final TeamBuilding teamBuilding = findWithTeamsAndUsers(roomUuid);

		return TeamBuildingResponse.builder()
			.teamBuildingInfo(makeTeamBuildingInfo(teamBuilding))
			.teamInfoList(teamBuilding.getTeams().stream().map(TeamInfo::makeTeamInfo).toList())
			.build();
	}

	@Transactional(readOnly = true)
	public TeamBuildingResponse findTeamBuilding(String roomUuid) {
		final TeamBuilding teamBuilding = findWithTeamsAndUsers(roomUuid);

		return TeamBuildingResponse.builder()
			.teamBuildingInfo(makeTeamBuildingInfo(teamBuilding))
			.teamInfoList(teamBuilding.getTeams().stream().map(TeamInfo::makeTeamInfo).toList())
			.userInfoList(teamBuilding.getUsers().stream().map(UserInfo::makeUserInfo).toList())
			.build();
	}

	private TeamBuilding findWithTeamsAndUsers(String uuid) {
		return teamBuildingRepository.findWithTeamsAndUsers(uuid)
			.orElseThrow(ExceptionInfo.INVALID_TEAM_BUILDING_UUID::exception);
	}

	public TeamBuilding findByUuid(String teamBuildingUuid) {
		return teamBuildingRepository.findByUuid(teamBuildingUuid)
			.orElseThrow(ExceptionInfo.INVALID_TEAM_BUILDING_UUID::exception);
	}
}
