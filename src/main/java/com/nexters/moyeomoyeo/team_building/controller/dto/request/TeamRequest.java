package com.nexters.moyeomoyeo.team_building.controller.dto.request;

import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TeamRequest {

	@NotNull
	private String name;
	@NotNull
	private String pmName;
	@NotNull
	private Position pmPosition;

	public static Team toEntity(TeamRequest request) {
		return Team.builder()
			.name(request.getName())
			.pmName(request.getPmName())
			.pmPosition(request.getPmPosition())
			.build();
	}
}
