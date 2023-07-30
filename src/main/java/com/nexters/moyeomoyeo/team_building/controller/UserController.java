package com.nexters.moyeomoyeo.team_building.controller;


import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.*;
import com.nexters.moyeomoyeo.team_building.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
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
