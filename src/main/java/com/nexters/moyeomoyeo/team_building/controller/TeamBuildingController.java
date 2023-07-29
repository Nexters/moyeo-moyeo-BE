package com.nexters.moyeomoyeo.team_building.controller;

import com.nexters.moyeomoyeo.team_building.controller.dto.RoomInfoResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.UserPickRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.UserPickResponse;
import com.nexters.moyeomoyeo.team_building.service.TeamBuildingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class TeamBuildingController {

	private final TeamBuildingService teamBuildingService;

	@GetMapping("/{roomUuid}/info")
	public ResponseEntity<RoomInfoResponse> findRoomInfo(@PathVariable(value = "roomUuid") String roomUuid) {
		return ResponseEntity.ok(teamBuildingService.findRoomInfo(roomUuid));
	}

	@PutMapping("/{roomUuid}/teams/{teamUuid}/users")
	public ResponseEntity<UserPickResponse> pickUsers(
		@PathVariable(value = "roomUuid") String roomUuid,
		@PathVariable(value = "teamUuid") String teamUuid,
		@RequestBody @Valid UserPickRequest userPickRequest) {
		return ResponseEntity.ok(teamBuildingService.pickUsers(roomUuid, teamUuid, userPickRequest));
	}

}
