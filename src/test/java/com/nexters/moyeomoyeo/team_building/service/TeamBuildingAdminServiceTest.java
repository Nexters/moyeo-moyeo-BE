package com.nexters.moyeomoyeo.team_building.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.nexters.moyeomoyeo.MoyeoMoyeoTest;
import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.common.exception.vo.CustomException;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.TeamBuildingRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.TeamRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.TeamBuildingResponse;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo;
import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.domain.repository.TeamBuildingRepository;
import com.nexters.moyeomoyeo.team_building.domain.repository.UserRepository;
import com.nexters.moyeomoyeo.team_building.fixture.FixtureTeamBuilding;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@MoyeoMoyeoTest
class TeamBuildingAdminServiceTest {

	@Autowired
	private TeamBuildingAdminService sut;

	@Autowired
	private TeamBuildingRepository teamBuildingRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void createTeamBuilding() {
		final TeamBuildingResponse teamBuildingResponse = sut.createTeamBuilding(TeamBuildingRequest.builder()
			.name("넥스터즈 팀빌딩이에요")
			.teams(List.of(
				TeamRequest.builder()
					.name("team1")
					.pmName("pm1")
					.pmPosition(Position.FRONT_END)
					.build(),
				TeamRequest.builder()
					.name("team2")
					.pmName("pm2")
					.pmPosition(Position.BACK_END)
					.build()))
			.build());;

		assertThat(teamBuildingResponse.getTeamBuildingInfo().getTeamBuildingName()).isEqualTo("넥스터즈 팀빌딩이에요");
		assertThat(teamBuildingResponse.getTeamInfoList().get(0).getTeamName()).isEqualTo("team1");
		assertThat(teamBuildingResponse.getTeamInfoList().get(0).getPmPosition()).isEqualTo(Position.FRONT_END);
	}


	@Transactional
	@Test
	@DisplayName("조정 단계에서 팀1 -> 팀2로 옮겨졌을 때")
	void adjustUserToDifferentTeam() {
		final TeamBuilding teamBuilding = teamBuildingRepository.save(FixtureTeamBuilding.teamBuilding(RoundStatus.ADJUSTED_ROUND));
		final Team team1 =  teamBuilding.getTeams().get(0);
		final Team team2 =  teamBuilding.getTeams().get(1);

		final User user = userRepository.save(User.builder()
			.name("넥터사람")
			.position(Position.BACK_END)
			.choices(List.of("1", "2", "3", "4"))
			.team(team1)
			.build());

		final UserInfo adjustedUser = sut.adjustUser(teamBuilding.getUuid(), user.getUuid(), team2.getUuid());
		assertThat(adjustedUser.getJoinedTeamUuid()).isEqualTo(team2.getUuid());
		assertThat(adjustedUser.getSelectedRound()).isEqualTo(RoundStatus.ADJUSTED_ROUND);
	}

	@Transactional
	@Test
	@DisplayName("조정 단계에서 할당됭 팀을 해제했을 때")
	void adjustUserToNotAssigned() {
		final TeamBuilding teamBuilding = teamBuildingRepository.save(FixtureTeamBuilding.teamBuilding(RoundStatus.ADJUSTED_ROUND));
		final Team team1 =  teamBuilding.getTeams().get(0);

		final User user = userRepository.save(User.builder()
			.name("넥터사람")
			.position(Position.BACK_END)
			.choices(List.of("1", "2", "3", "4"))
			.team(team1)
			.build());

		final UserInfo adjustedUser = sut.adjustUser(teamBuilding.getUuid(), user.getUuid(), null);
		assertThat(adjustedUser.getJoinedTeamUuid()).isNull();
		assertThat(adjustedUser.getSelectedRound()).isNull();
	}

	@Test
	void deleteTeamBuildingUser() {
		final TeamBuilding teamBuilding = teamBuildingRepository.save(FixtureTeamBuilding.teamBuilding(RoundStatus.START));

		final User user = userRepository.save(User.builder()
			.name("넥터사람")
			.position(Position.BACK_END)
			.choices(List.of("1", "2", "3", "4"))
			.teamBuildingUuid(teamBuilding.getUuid())
			.build());

		sut.deleteTeamBuildingUser(teamBuilding.getUuid(), user.getUuid());

		assertThat(userRepository.findByUuid(user.getUuid())).isEmpty();
	}

	@Test
	@Transactional
	void finishTeamBuilding() {
		final TeamBuilding teamBuilding = teamBuildingRepository.save(FixtureTeamBuilding.teamBuilding(RoundStatus.ADJUSTED_ROUND));

		sut.finishTeamBuilding(teamBuilding.getUuid());

		assertThat(teamBuilding.getRoundStatus()).isEqualTo(RoundStatus.COMPLETE);
		assertThat(teamBuilding.getTeams().get(0).getRoundStatus()).isEqualTo(RoundStatus.COMPLETE);
	}

	@Test
	@Transactional
	void startTeamBuilding() {
		final TeamBuilding teamBuilding = teamBuildingRepository.save(FixtureTeamBuilding.teamBuilding(RoundStatus.START));

		sut.startTeamBuilding(teamBuilding.getUuid());

		assertThat(teamBuilding.getRoundStatus()).isEqualTo(RoundStatus.FIRST_ROUND);
		assertThat(teamBuilding.getTeams().get(0).getRoundStatus()).isEqualTo(RoundStatus.FIRST_ROUND);
	}
}
