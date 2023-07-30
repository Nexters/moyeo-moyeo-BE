package com.nexters.moyeomoyeo.team_building.controller;


import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.*;
import com.nexters.moyeomoyeo.team_building.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class TeamController {

	private final TeamService teamService;

	@PostMapping("/{roomUuid}/teams")
	public ResponseEntity<List<RoomInfoResponse.TeamInfo>> createUser(@PathVariable(value = "roomUuid") String roomUuid,
																	  @RequestBody TeamsCreateRequest teamsCreateRequest) {
		return ResponseEntity.ok(teamService.createTeams(roomUuid, teamsCreateRequest));
	}
}
