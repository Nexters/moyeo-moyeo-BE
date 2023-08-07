package com.nexters.moyeomoyeo.team_building.controller.dto;

import com.nexters.moyeomoyeo.team_building.domain.constant.*;
import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserInfo {

	@Schema(description = "회원 고유값")
	private String uuid;
	@Schema(description = "회원 이름")
	private String userName;
	@Schema(description = "회원 포지션")
	private Position position;
	@Schema(description = "회원이 선택한 팀들(배열 순서대로 1지망, 2지망, 3지망, 4지망)")
	private List<String> choices;
	@Schema(description = "최종 빌딩 된 팀")
	private String joinedTeamUuid;
	@Builder.Default
	@Schema(description = "현재 팀 빌딩이 완료된 상태인지 여부")
	private boolean isSelectedTeam = false;
}
