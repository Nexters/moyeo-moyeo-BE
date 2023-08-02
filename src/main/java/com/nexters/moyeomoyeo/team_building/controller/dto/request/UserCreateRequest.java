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
	@Size(min = 4 , max = 4 , message = "선택한 지망이 부족합니다.")
	private List<String> choices;
}
