package com.nexters.moyeomoyeo.team_building.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TeamBuildingRequest {

	private String name;
	@NotNull
	private List<TeamRequest> teams;
}
