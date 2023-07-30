package com.nexters.moyeomoyeo.team_building.controller.dto;

import com.nexters.moyeomoyeo.team_building.domain.constant.*;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserInfo {

	private String uuid;
	private String userName;
	private Position position;
	private List<String> choices;
	private String joinedTeamUuid;
	@Builder.Default
	private boolean isSelectedTeam = false;
}
