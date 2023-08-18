package com.nexters.moyeomoyeo.notification.controller;

import com.nexters.moyeomoyeo.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@GetMapping(value = "/team-building/{teamBuildingUuid}/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> subscribe(@PathVariable("teamBuildingUuid") String teamBuildingUuid) {
		return ResponseEntity.ok()
			.header("X-Accel-Buffering", "no")
			.body(notificationService.subscribe(teamBuildingUuid));
	}
}
