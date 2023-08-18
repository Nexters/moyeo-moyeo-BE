package com.nexters.moyeomoyeo.notification.service;

import com.nexters.moyeomoyeo.notification.handler.SseEmitterHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 30;

	private final SseEmitterHandler handler;

	public void sendNotification(SseEmitter emitter, String name, Object data) {
		try {
			emitter.send(SseEmitter.event()
				.name(name)
				.data(data));
		} catch (Exception e) {
			log.error("fail to send message : {}", emitter);
		}
	}

	public SseEmitter subscribe(String teamBuildingUuid) {
		final SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		handler.add(teamBuildingUuid, emitter);
		sendNotification(emitter, "subscribe", "subscribe completed");

		return emitter;
	}

	public void broadCast(String teamBuildingUuid, String name, Object data) {
		for (final SseEmitter emitter : handler.getEmitters(teamBuildingUuid)) {
			sendNotification(emitter, name, data);
		}
	}
}


