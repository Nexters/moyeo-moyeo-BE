package com.nexters.moyeomoyeo.team_building.controller;

import com.nexters.moyeomoyeo.common.exception.dto.ExceptionResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.TeamBuildingRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.UserAdjustRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo;
import com.nexters.moyeomoyeo.team_building.service.TeamBuildingService;
import com.nexters.moyeomoyeo.team_building.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/team-building")
@RequiredArgsConstructor
@Tag(name = "어드민", description = "어드민 관련 팀빌딩 api 입니다.")
public class TeamBuildingAdminController {

	private final TeamBuildingService teamBuildingService;
	private final UserService userService;

	@Operation(summary = "팀 빌딩 생성 요청", description = "팀빌딩 방과 팀 리스트를 생성됩니다. ")
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TeamBuildingResponse.class)))
	@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
		@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
	@PostMapping
	public ResponseEntity<TeamBuildingResponse> createTeamBuilding(
		@RequestBody @Valid TeamBuildingRequest teamBuildingRequest) {
		return ResponseEntity.ok(teamBuildingService.createTeamBuilding(teamBuildingRequest));
	}

	@Operation(summary = "팀원 조정 (단일 유저) ", description = """
		운영진이 조정 단계에서 팀원을 조정합니다. \s
		event : adjust-user, data : UserInfo.class \s
		""")
	@PostMapping("/{teamBuildingUuid}/users/{userUuid}")
	public ResponseEntity<UserInfo> adjustUser(@PathVariable(value = "teamBuildingUuid") String teamBuildingUuid,
		@PathVariable(value = "userUuid") String userUuid, @RequestBody @Valid UserAdjustRequest userAdjustRequest) {
		return ResponseEntity.ok(
			teamBuildingService.adjustUser(teamBuildingUuid, userUuid, userAdjustRequest.getTeamUuid()));
	}

	@Operation(summary = "팀원 삭제 (단일 유저) ", description = """
		운영진이 팀원을 삭제합니다. \s
		event : delete-user, data : userUuid
		""")
	@DeleteMapping("/{teamBuildingUuid}/users/{userUuid}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "teamBuildingUuid") String teamBuildingUuid,
		@PathVariable(value = "userUuid") String userUuid) {
		userService.deleteUser(teamBuildingUuid, userUuid);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "팀 빌딩 마치기", description = """
		운영진이 조정 단계에서 팀빌딩을 마칩니다. \s
		event : finish-team-building, data : RoundStatus(COMPLETE) \s
		""")
	@PutMapping("/{teamBuildingUuid}/finish")
	public ResponseEntity<Void> finishTeamBuilding(@PathVariable(value = "teamBuildingUuid") String teamBuildingUuid) {
		teamBuildingService.finishTeamBuilding(teamBuildingUuid);
		return ResponseEntity.ok().build();
	}
}
