package com.nexters.moyeomoyeo.team_building.service;

import static com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse.TeamBuildingInfo.makeTeamBuildingInfo;
import static com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamInfo.isSelectDone;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.notification.service.NotificationService;
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
import com.nexters.moyeomoyeo.team_building.domain.entity.UserChoice;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamBuildingCoreService {

	private final TeamBuildingService teamBuildingService;
	private final UserService userService;
	private final NotificationService notificationService;

	/**
	 * 선택된 유저가 현재 라운드에서 지망한 팀이 유저를 선택한 팀과 일치하는지 유효성 체크.
	 *
	 * @param team        유저를 선택한 팀
	 * @param pickedUsers 선택된 유저 목록
	 * @return 일치여부
	 */
	private static boolean isChosenTeam(Team team, List<User> pickedUsers) {
		final RoundStatus roundStatus = team.getRoundStatus();
		for (final User user : pickedUsers) {
			final UserChoice choice = user.findChoice(roundStatus.getWeight());
			if (!Objects.equals(choice.getTeamUuid(), team.getUuid())) {
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

	@Transactional(readOnly = true)
	public TeamBuildingResponse findTeamBuilding(String teamBuildingUuid) {
		final TeamBuilding teamBuilding = teamBuildingService.findByUuid(teamBuildingUuid);

		final List<User> users = userService.findByTeamBuildingId(teamBuilding.getId());

		return TeamBuildingResponse.builder()
			.teamBuildingInfo(makeTeamBuildingInfo(teamBuilding))
			.teamInfoList(teamBuilding.getTeams().stream().map(TeamInfo::makeTeamInfo).toList())
			.userInfoList(users.stream().map(UserInfo::makeUserInfo).toList())
			.build();
	}

	@Transactional
	public UserPickResponse pickUsers(String teamBuildingUuid, String teamUuid, UserPickRequest userPickRequest) {
		final TeamBuilding teamBuilding = teamBuildingService.findByUuid(teamBuildingUuid);
		final RoundStatus teamBuildingRoundStatus = teamBuilding.getRoundStatus();

		if (RoundStatus.COMPLETE == teamBuildingRoundStatus) {
			throw ExceptionInfo.COMPLETED_TEAM_BUILDING.exception();
		}

		final Team targetTeam = teamBuilding.getTeams()
			.stream()
			.filter(team -> teamUuid.equals(team.getUuid()))
			.findFirst()
			.orElseThrow(ExceptionInfo.INVALID_TEAM_UUID::exception);

		if (isSelectDone(teamBuildingRoundStatus, targetTeam.getRoundStatus())) {
			throw ExceptionInfo.DUPLICATED_PICK_REQUEST.exception();
		}

		final List<String> userUuids = userPickRequest.getUserUuids();
		final List<User> pickedUsers = userService.findByUuidIn(userUuids);

		if (!isChosenTeam(targetTeam, pickedUsers)) {
			throw ExceptionInfo.BAD_REQUEST_FOR_USER_PICK.exception();
		}

		for (final User user : pickedUsers) {
			user.addTeam(targetTeam);
		}
		targetTeam.nextRound();

		PickUserResponse userResponse = PickUserResponse.builder()
			.teamName(targetTeam.getName())
			.teamUuid(teamUuid)
			.pickUserUuids(userUuids)
			.build();

		notificationService.broadCast(teamBuilding.getUuid(), "pick-user", userResponse);

		if (isAllTeamSelected(teamBuilding.getTeams(), teamBuildingRoundStatus)) {
			final RoundStatus nextStatus = teamBuilding.nextRound();
			notificationService.broadCast(teamBuilding.getUuid(), "change-round", nextStatus);
		}

		return UserPickResponse.builder()
			.userInfoList(targetTeam.getUsers().stream().map(UserInfo::makeUserInfo).toList())
			.build();
	}
}
