package com.nexters.moyeomoyeo.team_building.controller.dto.request;

import static com.nexters.moyeomoyeo.team_building.domain.entity.User.MAX_CHOICE_SIZE;

import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UserRequest {

	@NotNull
	private String name;
	@NotNull
	private Position position;
	private String profileLink;
	@NotNull
	@Size(max = MAX_CHOICE_SIZE, message = "최대 선택 지망을 초과했습니다.")
	private List<String> choices;


}
