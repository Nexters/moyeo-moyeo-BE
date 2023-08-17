package com.nexters.moyeomoyeo.team_building.controller;

import com.nexters.moyeomoyeo.common.exception.dto.ExceptionResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.UserPickRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserPickResponse;
import com.nexters.moyeomoyeo.team_building.service.TeamBuildingCoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team-building")
@Tag(name = "팀 빌딩", description = "팀 빌딩 core api 입니다.")
@RequiredArgsConstructor
public class TeamBuildingController {

	private final TeamBuildingCoreService coreService;

	@Operation(summary = "팀 빌딩 현황 조회", description = "팀빌딩 현황 데이터가 조회됩니다. ")
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TeamBuildingResponse.class)))
	@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
		@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
	@GetMapping("/{teamBuildingUuid}")
	public ResponseEntity<TeamBuildingResponse> findTeamBuilding(
		@PathVariable(value = "teamBuildingUuid") String teamBuildingUuid) {
		return ResponseEntity.ok(coreService.findTeamBuilding(teamBuildingUuid));
	}


	@Operation(summary = "팀 빌딩 팀원 선택 요청", description = """
		PM이 지망 별 팀원 선택을 할 때 사용합니다. \s
		event : pick-user, data : {"teamUuid" : "string", "teamName" : "string", "pickUserUuids" : ["string1", "string2"]} \s
		event : change-round, data : RoundStatus(FIRST_ROUND, ADJUSTED_ROUND...)
		""")
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserPickResponse.class)))
	@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
	@PostMapping("/{teamBuildingUuid}/teams/{teamUuid}/users")
	public ResponseEntity<UserPickResponse> pickUsers(
		@PathVariable(value = "teamBuildingUuid") String teamBuildingUuid,
		@PathVariable(value = "teamUuid") String teamUuid,
		@RequestBody @Valid UserPickRequest userPickRequest) {
		return ResponseEntity.ok(coreService.pickUsers(teamBuildingUuid, teamUuid, userPickRequest));
	}

}
