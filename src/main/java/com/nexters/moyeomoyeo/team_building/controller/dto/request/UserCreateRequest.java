package com.nexters.moyeomoyeo.team_building.controller.dto.request;

import com.nexters.moyeomoyeo.team_building.domain.constant.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserCreateRequest {
	@NotNull
	private String name;
	@NotNull
	private Position position;
	@NotNull
	private List<String> choices;
}
