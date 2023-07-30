package com.nexters.moyeomoyeo.team_building.controller;


import com.nexters.moyeomoyeo.common.exception.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.*;
import com.nexters.moyeomoyeo.team_building.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Tag(name = "팀", description = "팀 관련 api 입니다.")
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class TeamController {

	private final TeamService teamService;

	@Operation(summary = "팀 생성 요청", description = "팀이 생성됩니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(schema = @Schema(implementation = TeamInfo.class))),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST",
			content = {@Content(mediaType = "application/json",
				schema = @Schema(implementation = ExceptionResponse.class))})
	})
	@PostMapping("/{roomUuid}/teams")
	public ResponseEntity<List<TeamInfo>> createUser(@PathVariable(value = "roomUuid") String roomUuid,
													 @RequestBody TeamsCreateRequest teamsCreateRequest) {
		return ResponseEntity.ok(teamService.createTeams(roomUuid, teamsCreateRequest));
	}
}
