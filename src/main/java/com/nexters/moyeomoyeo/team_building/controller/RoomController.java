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
public class RoomController {
	private final RoomService roomService;

	@PostMapping
	public ResponseEntity<RoomInfoResponse.RoomInfo> createRoom(@RequestBody RoomCreateRequest roomCreateRequest) {
		return ResponseEntity.ok(roomService.createRoom(roomCreateRequest));
	}

	@GetMapping("/{roomUuid}")
	ResponseEntity<RoomInfoResponse> entranceRoom(@PathVariable(value = "roomUuid") String roomUuid) {
		return ResponseEntity.ok(roomService.findRoomInfo(roomUuid));
	}
}

