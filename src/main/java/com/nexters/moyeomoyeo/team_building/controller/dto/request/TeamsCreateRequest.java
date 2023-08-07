package com.nexters.moyeomoyeo.team_building.controller.dto.request;

import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TeamsCreateRequest {
	private List<TeamCreateRequest> teams;
}
