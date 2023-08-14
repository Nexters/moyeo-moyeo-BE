package com.nexters.moyeomoyeo.team_building.controller;


import com.nexters.moyeomoyeo.common.exception.dto.ExceptionResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.UserRequest;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "유저 설문", description = "유저 설문 페이지 관련 api 입니다.")
@RequestMapping("/api/surveys/team-building")
@RequiredArgsConstructor
public class UserSurveyController {

	private final UserService userService;
	private final TeamBuildingService teamBuildingService;

	@Operation(summary = "회원 생성 요청", description = """
		회원이 생성됩니다. \s
		event : create-user, data : UserInfo.class""")
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserInfo.class)))
	@ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {
		@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
	@PostMapping("/{teamBuildingUuid}/users")
	public ResponseEntity<UserInfo> createUser(@PathVariable(value = "teamBuildingUuid") String teamBuildingUuid,
		@RequestBody @Valid UserRequest userRequest) {
		return ResponseEntity.ok(userService.createUser(teamBuildingUuid, userRequest));
	}

	@Operation(summary = "팀 빌딩 팀 데이터 조회", description = "팀 빌딩, 팀 정보가 조회됩니다. (유저 선택 정보 제외) ")
	@GetMapping("/{teamBuildingUuid}/teams")
	public ResponseEntity<TeamBuildingResponse> findTeamBuildingAndTeams(
		@PathVariable(value = "teamBuildingUuid") String teamBuildingUuid) {
		return ResponseEntity.ok(teamBuildingService.findTeamBuildingAndTeams(teamBuildingUuid));
	}
}
