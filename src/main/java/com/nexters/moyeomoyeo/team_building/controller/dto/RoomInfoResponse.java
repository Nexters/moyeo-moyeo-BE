package com.nexters.moyeomoyeo.team_building.controller.dto;

import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
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
public class RoomInfoResponse {

	private RoomInfo roomInfo;
	private List<TeamInfo> teamInfoList;
	private List<UserInfo> userInfoList;

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Builder
	public static class RoomInfo {

		private String roomUrl;
		private RoundStatus roundStatus;
	}
}
