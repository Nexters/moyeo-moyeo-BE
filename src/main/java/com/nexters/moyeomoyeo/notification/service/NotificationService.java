package com.nexters.moyeomoyeo.notification.service;

import com.nexters.moyeomoyeo.notification.handler.SseEmitterHandler;
import java.io.IOException;
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
		} catch (IOException e) {
			log.error("fail to send message : {}", emitter);
		}
	}

	public SseEmitter subscribe() {
		final SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		sendNotification(emitter, "subscribe", "subscribe completed");
		handler.add(emitter);

		return emitter;
	}

	public void broadCast(String name, Object data) {
		for (final SseEmitter emitter : handler.getEmitters()) {
			sendNotification(emitter, name, data);
		}
	}
}

