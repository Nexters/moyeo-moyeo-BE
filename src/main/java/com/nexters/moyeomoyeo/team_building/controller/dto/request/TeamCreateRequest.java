package com.nexters.moyeomoyeo.team_building.controller.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TeamCreateRequest {
	@NotNull
	private String name;
	@NotNull
	private String pmName;
}
