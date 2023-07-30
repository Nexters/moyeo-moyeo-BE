package com.nexters.moyeomoyeo.team_building.controller;


import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.*;
import com.nexters.moyeomoyeo.team_building.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController@Tag(name = "회원", description = "회원 관련 api 입니다.")
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/{roomUuid}/user")
	public ResponseEntity<UserInfo> createUser(@PathVariable(value = "roomUuid") String roomUuid,
													   @RequestBody UserCreateRequest userCreateRequest) {
		return ResponseEntity.ok(userService.createUser(roomUuid,userCreateRequest));
	}
}
