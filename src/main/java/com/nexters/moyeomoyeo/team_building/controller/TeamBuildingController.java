package com.nexters.moyeomoyeo.team_building.controller;

import com.nexters.moyeomoyeo.common.exception.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.service.TeamBuildingService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class TeamBuildingController {

	private final TeamBuildingService teamBuildingService;


	@Operation(summary = "팀 빌딩 방 전체 데이터 조회 요청", description = "팀빌딩 내에 있는 데이터가 조회됩니다. ")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(schema = @Schema(implementation = RoomInfoResponse.class))),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST",
			content = {@Content(mediaType = "application/json",
				schema = @Schema(implementation = ExceptionResponse.class))})
	})
	@GetMapping("/{roomUuid}/info")
	public ResponseEntity<RoomInfoResponse> findRoomInfo(@PathVariable(value = "roomUuid") String roomUuid) {
		return ResponseEntity.ok(teamBuildingService.findRoomInfo(roomUuid));
	}


	@Operation(summary = "팀 빌딩 팀원 선택 요청", description = "PM이 지망 별 팀원 선택을 할 때 사용합니다. ")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(schema = @Schema(implementation = UserPickResponse.class))),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST",
			content = {@Content(mediaType = "application/json",
				schema = @Schema(implementation = ExceptionResponse.class))})
	})
	@PostMapping("/{roomUuid}/teams/{teamUuid}/users")
	public ResponseEntity<UserPickResponse> pickUsers(
		@PathVariable(value = "roomUuid") String roomUuid,
		@PathVariable(value = "teamUuid") String teamUuid,
		@RequestBody @Valid UserPickRequest userPickRequest) {
		return ResponseEntity.ok(teamBuildingService.pickUsers(roomUuid, teamUuid, userPickRequest));
	}

}
