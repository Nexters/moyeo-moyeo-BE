package com.nexters.moyeomoyeo.notification.handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
public class SseEmitterHandler {

	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	public List<SseEmitter> getEmitters() {
		return emitters;
	}

	public void add(SseEmitter emitter) {
		this.emitters.add(emitter);
		log.info("new emitter added: {}", emitter);
		log.info("emitter list size: {}", emitters.size());
		emitter.onCompletion(() -> {
			log.info("onCompletion callback");
			this.emitters.remove(emitter);
		});
		emitter.onTimeout(() -> {
			log.info("onTimeout callback");
			this.emitters.remove(emitter);
		});
	}
}
