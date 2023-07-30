package com.nexters.moyeomoyeo.team_building.controller.dto;

import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import java.util.List;

import io.swagger.v3.oas.annotations.media.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoomInfoResponse {

	@Schema(description = "팀 빌딩 정보")
	private RoomInfo roomInfo;
	@Schema(description = "팀빌딩에 참여하는 팀 정보")
	private List<TeamInfo> teamInfoList;
	@Schema(description = "팀빌딩에 참여하는 회원 정보")
	private List<UserInfo> userInfoList;

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Builder
	public static class RoomInfo {
		@Schema(description = "팀 빌딩 방 고유값")
		private String roomUrl;
		@Schema(description = "팀 빌딩 진행 상태")
		private RoundStatus roundStatus;
	}
}
