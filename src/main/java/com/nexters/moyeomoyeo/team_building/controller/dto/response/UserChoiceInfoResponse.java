package com.nexters.moyeomoyeo.team_building.controller.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserChoiceInfoResponse {

	private Long id;
	private Integer choiceOrder;
	private UserInfo user;
	private TeamInfo team;
}
