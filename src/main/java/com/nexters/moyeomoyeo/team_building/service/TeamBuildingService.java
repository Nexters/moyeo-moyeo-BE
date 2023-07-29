package com.nexters.moyeomoyeo.team_building.service;

import static com.nexters.moyeomoyeo.team_building.controller.dto.RoomInfoResponse.TeamInfo.isSelectDone;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.team_building.controller.dto.RoomInfoResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.RoomInfoResponse.RoomInfo;
import com.nexters.moyeomoyeo.team_building.controller.dto.RoomInfoResponse.TeamInfo;
import com.nexters.moyeomoyeo.team_building.controller.dto.RoomInfoResponse.UserInfo;
import com.nexters.moyeomoyeo.team_building.controller.dto.UserPickRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.UserPickResponse;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.Room;
import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.domain.repository.RoomRepository;
import com.nexters.moyeomoyeo.team_building.domain.repository.TeamRepository;
import com.nexters.moyeomoyeo.team_building.domain.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class TeamBuildingService {

	private final RoomRepository roomRepository;
	private final TeamRepository teamRepository;
	private final UserRepository userRepository;

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

	private static TeamInfo makeTeamInfo(Team team) {
		return TeamInfo.builder()
			.uuid(team.getTeamUuid())
			.teamName(team.getName())
			.isSelectDone(isSelectDone(team.getRoom().getRoundStatus(), team.getRoundStatus()))
			.pmName(team.getPmName())
			.pmPosition(team.getPmPosition())
			.build();
	}

	private static RoomInfo makeRoomInfo(Room room) {
		return RoomInfo.builder()
			.roomUrl(room.getRoomUuid())
			.roundStatus(room.getRoundStatus())
			.build();
	}

	private static boolean isValidUser(List<String> userUuids, List<User> pickedUsers) {
		return pickedUsers.size() == userUuids.size();
	}

	public static boolean isSelectedTeam(List<String> choices, String joinedTeamUuid) {
		if (CollectionUtils.isEmpty(choices)) {
			return false;
		}

		return choices.contains(joinedTeamUuid);
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

	@Transactional(readOnly = true)
	public RoomInfoResponse findRoomInfo(String roomUuid) {
		final Room room = roomRepository.findByRoomUuid(roomUuid)
			.orElseThrow(ExceptionInfo.INVALID_ROOM_UUID::exception);

		return RoomInfoResponse.builder()
			.roomInfo(makeRoomInfo(room))
			.teamInfoList(room.getTeams().stream().map(TeamBuildingService::makeTeamInfo).toList())
			.userInfoList(room.getUsers().stream().map(TeamBuildingService::makeUserInfo).toList())
			.build();
	}

	@Transactional
	public UserPickResponse pickUsers(String teamUuid, UserPickRequest userPickRequest) {
		List<String> userUuids = userPickRequest.getUserUuids();
		final List<User> pickedUsers = userRepository.findByUuidIn(userUuids);
		final Team team = teamRepository.findByTeamUuid(teamUuid)
			.orElseThrow(ExceptionInfo.INVALID_TEAM_UUID::exception);

		if (!isValidUser(userUuids, pickedUsers) || !isChosenTeam(team, pickedUsers)) {
			throw ExceptionInfo.BAD_REQUEST_FOR_USER_PICK.exception();
		}

		for (final User user : pickedUsers) {
			user.addTeam(team);
		}
		team.updateRoomStatus();

		return UserPickResponse.builder()
			.userInfoList(team.getUsers().stream().map(TeamBuildingService::makeUserInfo).toList())
			.build();
	}
}
