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

@RestController@Tag(name = "회원", description = "회원 관련 api 입니다.")
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원 생성 요청", description = "회원이 생성됩니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(schema = @Schema(implementation = UserInfo.class))),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST",
			content = {@Content(mediaType = "application/json",
				schema = @Schema(implementation = ExceptionResponse.class))})
	})
	@PostMapping("/{roomUuid}/user")
	public ResponseEntity<UserInfo> createUser(@PathVariable(value = "roomUuid") String roomUuid,
													   @RequestBody UserCreateRequest userCreateRequest) {
		return ResponseEntity.ok(userService.createUser(roomUuid,userCreateRequest));
	}
}
