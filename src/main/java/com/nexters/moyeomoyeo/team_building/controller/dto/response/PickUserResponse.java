package com.nexters.moyeomoyeo.team_building.controller.dto.response;

import lombok.*;

import java.util.*;

@Getter
@RequiredArgsConstructor
@Builder
public class PickUserResponse {
	final String teamUuid;
	final String teamName;
	final List<String> pickUserUuids;
}
