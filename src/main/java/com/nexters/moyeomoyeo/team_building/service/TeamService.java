package com.nexters.moyeomoyeo.team_building.service;


import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.*;
import com.nexters.moyeomoyeo.team_building.domain.entity.*;
import com.nexters.moyeomoyeo.team_building.domain.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.nexters.moyeomoyeo.common.constant.ExceptionInfo.INVALID_TEAM_UUID;
import static com.nexters.moyeomoyeo.team_building.controller.dto.TeamInfo.isSelectDone;

@Service
@RequiredArgsConstructor
public class TeamService {
	private final RoomService roomService;
	private final TeamRepository teamRepository;

	public List<TeamInfo> createTeams(String roomUuid, List<TeamCreateRequest> teamCreateRequests) {
		List<Team> savedTeam = new ArrayList<>();
		Room targetRoom = roomService.findByRoomUuid(roomUuid);

		for (TeamCreateRequest teamInfo : teamCreateRequests) {
			savedTeam.add(teamRepository.save(toTeam(targetRoom, teamInfo)));
		}

		targetRoom.addTeams(savedTeam);

		return savedTeam.stream()
			.map(TeamService::makeTeamInfo)
			.toList();
	}

	public Team findByTeamUuid(String teamUuid) {
		return teamRepository.findByTeamUuid(teamUuid).orElseThrow(
			() -> INVALID_TEAM_UUID.exception());
	}

	private static Team toTeam(Room targetRoom, TeamCreateRequest teamInfo) {
		return Team.create(teamInfo.getName(), teamInfo.getPmName(), targetRoom);
	}

	public static TeamInfo makeTeamInfo(Team team) {
		return TeamInfo.builder()
			.uuid(team.getTeamUuid())
			.teamName(team.getName())
			.isSelectDone(isSelectDone(team.getRoom().getRoundStatus(), team.getRoundStatus()))
			.pmName(team.getPmName())
			.pmPosition(team.getPmPosition())
			.build();
	}
}
