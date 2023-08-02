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

@Tag(name = "팀빌딩", description = "방 관련 api 입니다.")
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
	private final RoomService roomService;

	@Operation(summary = "팀 빌딩 방 생성 요청", description = "팀빌딩 방이 생성됩니다. ")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(schema = @Schema(implementation = RoomInfoResponse.RoomInfo.class))),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST",
			content = {@Content(mediaType = "application/json",
				schema = @Schema(implementation = ExceptionResponse.class))})
	})
	@PostMapping
	public ResponseEntity<RoomInfoResponse.RoomInfo> createRoom(@RequestBody RoomCreateRequest roomCreateRequest) {
		return ResponseEntity.ok(roomService.createRoom(roomCreateRequest));
	}
}

