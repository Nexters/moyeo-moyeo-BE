package com.nexters.moyeomoyeo.team_building.service;

import com.nexters.moyeomoyeo.common.constant.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.domain.entity.*;
import com.nexters.moyeomoyeo.team_building.domain.repository.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final RoomRepository roomRepository;

	@Transactional(readOnly = true)
	public RoomInfoResponse findRoomInfo(String roomUuid) {
		final Room room = roomRepository.findByRoomUuid(roomUuid)
			.orElseThrow(ExceptionInfo.INVALID_ROOM_UUID::exception);

		return RoomInfoResponse.builder()
			.roomInfo(makeRoomInfo(room))
			.teamInfoList(room.getTeams().stream().map(TeamService::makeTeamInfo).toList())
			.userInfoList(room.getUsers().stream().map(UserService::makeUserInfo).toList())
			.build();
	}

	@Transactional(readOnly = true)
	public Room findByRoomUuid(String roomUuid) {
		return roomRepository.findByRoomUuid(roomUuid)
			.orElseThrow(ExceptionInfo.INVALID_ROOM_UUID::exception);
	}

	private static RoomInfoResponse.RoomInfo makeRoomInfo(Room room) {
		return RoomInfoResponse.RoomInfo.builder()
			.roomUrl(room.getRoomUuid())
			.roundStatus(room.getRoundStatus())
			.build();
	}
}
