package com.nexters.moyeomoyeo.team_building.fixture;

import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import java.util.List;

public class FixtureTeamBuilding {

	public static TeamBuilding teamBuilding(RoundStatus roundStatus) {
		return TeamBuilding.builder()
			.roundStatus(roundStatus)
			.name("넥스터즈 팀빌딩")
			.teams(List.of(
				Team.builder()
					.name("team1")
					.pmPosition(Position.IOS)
					.roundStatus(roundStatus)
					.build(),
				Team.builder()
					.name("team2")
					.pmPosition(Position.ANDROID)
					.roundStatus(roundStatus)
					.build(),
				Team.builder()
					.name("team3")
					.pmPosition(Position.FRONT_END)
					.roundStatus(roundStatus)
					.build(),
				Team.builder()
					.name("team4")
					.pmPosition(Position.BACK_END)
					.roundStatus(roundStatus)
					.build()))
			.build();
	}
}
