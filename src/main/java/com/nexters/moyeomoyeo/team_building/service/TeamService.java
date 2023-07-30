package com.nexters.moyeomoyeo.team_building.service;


import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.*;
import com.nexters.moyeomoyeo.team_building.domain.entity.*;
import com.nexters.moyeomoyeo.team_building.domain.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.nexters.moyeomoyeo.team_building.controller.dto.RoomInfoResponse.TeamInfo.isSelectDone;

@Service
@RequiredArgsConstructor
public class TeamService {
	private final RoomService roomService;
	private final TeamRepository teamRepository;

	public List<RoomInfoResponse.TeamInfo> createTeams(String roomUuid, TeamsCreateRequest teamsCreateRequest) {
		List<RoomInfoResponse.TeamInfo> teamsCreatedResponse = new ArrayList<>();
		Room targetRoom = roomService.findByRoomUuid(roomUuid);

		for (TeamCreateRequest teamInfo : teamsCreateRequest.getTeams()) {
			Team saved = teamRepository.save(toTeam(targetRoom, teamInfo));
			teamsCreatedResponse.add(makeTeamInfo(saved));
		}

		return teamsCreatedResponse;
	}

	private static Team toTeam(Room targetRoom, TeamCreateRequest teamInfo) {
		return Team.create(teamInfo.getName(), teamInfo.getPmName(), targetRoom);
	}

	public static RoomInfoResponse.TeamInfo makeTeamInfo(Team team) {
		return RoomInfoResponse.TeamInfo.builder()
			.uuid(team.getTeamUuid())
			.teamName(team.getName())
			.isSelectDone(isSelectDone(team.getRoom().getRoundStatus(), team.getRoundStatus()))
			.pmName(team.getPmName())
			.pmPosition(team.getPmPosition())
			.build();
	}
}
