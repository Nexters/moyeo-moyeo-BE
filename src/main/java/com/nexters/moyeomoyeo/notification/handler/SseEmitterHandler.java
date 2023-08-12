package com.nexters.moyeomoyeo.notification.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
public class SseEmitterHandler {

	// key : teamBuildingUuid
	private final Map<String, List<SseEmitter>> emitterMap = new HashMap<>();

	public List<SseEmitter> getEmitters(String teamBuildingUuid) {
		final List<SseEmitter> emitterList = emitterMap.get(teamBuildingUuid);

		if (Objects.isNull(emitterList)) {
			return Collections.emptyList();
		}

		return emitterList;
	}

	public void add(String teamBuildingUuid, SseEmitter emitter) {
		final List<SseEmitter> emitterList = Objects.isNull(this.emitterMap.get(teamBuildingUuid)) ?
			new CopyOnWriteArrayList<>() : this.emitterMap.get(teamBuildingUuid);

		emitterList.add(emitter);
		emitterMap.put(teamBuildingUuid, emitterList);

		log.info("new emitter added: {}", emitter);
		log.info("emitter list size: {}", emitterList.size());
		emitter.onCompletion(() -> {
			log.info("onCompletion callback");
			emitterList.remove(emitter);
		});
		emitter.onTimeout(() -> {
			log.info("onTimeout callback");
			emitterList.remove(emitter);
		});
	}
}
