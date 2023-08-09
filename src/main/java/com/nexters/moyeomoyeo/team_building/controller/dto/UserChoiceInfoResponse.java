package com.nexters.moyeomoyeo.team_building.controller.dto;

import lombok.*;

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
