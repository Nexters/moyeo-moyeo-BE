package com.nexters.moyeomoyeo.team_building.service;


import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.notification.service.NotificationService;
import com.nexters.moyeomoyeo.team_building.controller.dto.RoomInfoResponse;

import com.nexters.moyeomoyeo.team_building.controller.dto.UserPickRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.UserPickResponse;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.Room;
import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.*;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

import static com.nexters.moyeomoyeo.team_building.service.UserService.isSelectedTeam;

@Service
@RequiredArgsConstructor
public class TeamBuildingService {

	private final NotificationService notificationService;
	private final RoomService roomService;
	private final TeamService teamService;

	@Transactional
	public RoomInfoResponse createTeamBuilding(TeamBuildingCreateRequest teamBuildingCreateRequest) {
		RoomInfoResponse.RoomInfo savedRoomInfo = roomService.createRoom(teamBuildingCreateRequest.getName());

		List<TeamInfo> savedTeams = teamService.createTeams(savedRoomInfo.getRoomUrl(), teamBuildingCreateRequest.getTeams());

		return RoomInfoResponse.builder()
			.roomInfo(savedRoomInfo)
			.teamInfoList(savedTeams)
			.userInfoList(null)
			.build();
	}

	private static UserInfo makeUserInfo(User user) {
		final List<String> choices = user.getChoices().stream()
			.map(userChoice -> userChoice.getTeam().getTeamUuid())
			.toList();

		final String joinedTeamUuid = Objects.isNull(user.getTeam()) ? null : user.getTeam().getTeamUuid();

		return UserInfo.builder()
			.uuid(user.getUserUuid())
			.userName(user.getName())
			.position(user.getPosition())
			.choices(choices)
			.joinedTeamUuid(joinedTeamUuid)
			.isSelectedTeam(isSelectedTeam(choices, joinedTeamUuid))
			.build();
	}


	@Transactional(readOnly = true)
	public RoomInfoResponse findRoomInfo(String roomUuid) {
		return roomService.findRoomInfo(roomUuid);
	}

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
			if (!Objects.equals(user.findChoiceTeam(roundStatus.getWeight()).getTeam().getTeamUuid(),
				team.getTeamUuid())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 모든 팀이 선택을 완료했는지 판단.
	 *
	 * @param teams           room 의 team 들
	 * @param roomRoundStatus 현재 room 의 round 상태
	 * @return 완료됐으면 true
	 */
	private static boolean isAllTeamSelected(List<Team> teams, RoundStatus roomRoundStatus) {
		for (final Team team : teams) {
			if (roomRoundStatus == team.getRoundStatus()) {
				return false;
			}
		}

		return true;
	}


	@Transactional
	public UserPickResponse pickUsers(String roomUuid, String teamUuid, UserPickRequest userPickRequest) {
		final Room room = roomService.findByRoomUuid(roomUuid);

		if (RoundStatus.COMPLETE == room.getRoundStatus()) {
			throw ExceptionInfo.COMPLETED_TEAM_BUILDING.exception();
		}

		final Team targetTeam = room.getTeams().stream().filter(team -> teamUuid.equals(team.getTeamUuid())).findFirst().orElseThrow(ExceptionInfo.INVALID_TEAM_UUID::exception);

		final List<String> userUuids = userPickRequest.getUserUuids();
		final List<User> pickedUsers = room.getUsers().stream().filter(user -> userUuids.contains(user.getUserUuid())).toList();

		if (!isValidUser(userUuids, pickedUsers) || !isChosenTeam(targetTeam, pickedUsers)) {
			throw ExceptionInfo.BAD_REQUEST_FOR_USER_PICK.exception();
		}

		for (final User user : pickedUsers) {
			user.addTeam(targetTeam);
		}

		targetTeam.updateRoomStatus();
		if (isAllTeamSelected(room.getTeams(), room.getRoundStatus())) {
			room.updateRoomStatus();
		}

		return UserPickResponse.builder().userInfoList(targetTeam.getUsers().stream().map(UserService::makeUserInfo).toList()).build();
		notificationService.broadCast("pick-user", userUuids);

		return UserPickResponse.builder()
			.userInfoList(targetTeam.getUsers().stream().map(TeamBuildingService::makeUserInfo).toList())
			.build();
	}
}
