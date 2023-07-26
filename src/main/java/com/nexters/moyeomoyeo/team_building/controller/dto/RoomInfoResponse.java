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

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Builder
	public static class TeamInfo {

		private String uuid;
		private String teamName;
		private String pmName;
		private Position pmPosition;
		private boolean isSelectDone;

		public static boolean isSelectDone(RoundStatus roomStatus, RoundStatus teamStatus) {
			if (RoundStatus.COMPLETE == roomStatus) {
				return true;
			}

			return roomStatus.getWeight() < teamStatus.getWeight();
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Builder
	public static class UserInfo {

		private String uuid;
		private String userName;
		private Position position;
		private List<String> choices;
		private boolean isSelectedTeam;
		private String joinedTeamUuid;
	}
}
