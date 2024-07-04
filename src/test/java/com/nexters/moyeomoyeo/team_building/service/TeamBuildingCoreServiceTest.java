package com.nexters.moyeomoyeo.team_building.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nexters.moyeomoyeo.MoyeoMoyeoTest;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.UserPickRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserPickResponse;
import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.domain.repository.TeamBuildingRepository;
import com.nexters.moyeomoyeo.team_building.domain.repository.UserRepository;
import com.nexters.moyeomoyeo.team_building.fixture.FixtureTeamBuilding;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@MoyeoMoyeoTest
class TeamBuildingCoreServiceTest {

	@Autowired
	private TeamBuildingCoreService sut;

	@Autowired
	private TeamBuildingRepository teamBuildingRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Test
	@DisplayName("첫번째 라운드에서 한 팀이 유저 선택 완료후 다음라운드로 넘어가는지")
	void pickUserOne() {
		final TeamBuilding teamBuilding = teamBuildingRepository.save(FixtureTeamBuilding.teamBuilding(RoundStatus.FIRST_ROUND));

		final List<Team> teams = teamBuilding.getTeams();
		final Team sutTeam = teams.get(0);
		final User user = userRepository.save(User.builder()
			.name("넥터사람")
			.position(Position.BACK_END)
			.choices(List.of(sutTeam.getUuid(), teams.get(1).getUuid(), teams.get(2).getUuid(), teams.get(3).getUuid()))
			.teamBuildingUuid(teamBuilding.getUuid())
			.build());

		final UserPickResponse userPickResponse = sut.pickUsers(teamBuilding.getUuid(), sutTeam.getUuid(), UserPickRequest.builder()
			.userUuids(List.of(user.getUuid()))
			.build());

		assertThat(userPickResponse.getUserInfoList().get(0).getUserName()).isEqualTo("넥터사람");
		assertThat(userPickResponse.getUserInfoList().get(0).getPosition()).isEqualTo(Position.BACK_END);
		assertThat(userPickResponse.getUserInfoList().get(0).getSelectedRound()).isEqualTo(RoundStatus.FIRST_ROUND);
		assertThat(sutTeam.getRoundStatus()).isEqualTo(RoundStatus.SECOND_ROUND);
	}

	@Transactional
	@Test
	@DisplayName("첫번째 라운드에서 한 팀이 유저 선택을 아무도 하지 않은 후 다음라운드로 넘어가는지")
	void pickUserNoOne() {
		final TeamBuilding teamBuilding = teamBuildingRepository.save(FixtureTeamBuilding.teamBuilding(RoundStatus.FIRST_ROUND));

		final List<Team> teams = teamBuilding.getTeams();
		final Team sutTeam = teams.get(0);

		final UserPickResponse userPickResponse = sut.pickUsers(teamBuilding.getUuid(), sutTeam.getUuid(), UserPickRequest.builder()
			.userUuids(List.of())
			.build());

		assertThat(userPickResponse.getUserInfoList()).isEmpty();
		assertThat(sutTeam.getRoundStatus()).isEqualTo(RoundStatus.SECOND_ROUND);
	}

	@Transactional
	@Test
	@DisplayName("첫번째 라운드에서 모든 팀이 유저 선택 완료 후 팀빌딩이 다음라운드로 넘어가는지")
	void pickUserALL() {
		final TeamBuilding teamBuilding = teamBuildingRepository.save(FixtureTeamBuilding.teamBuilding(RoundStatus.FIRST_ROUND));

		final List<Team> teams = teamBuilding.getTeams();

		final User user1 = userRepository.save(User.builder()
			.name("넥터사람1")
			.position(Position.BACK_END)
			.choices(List.of(teams.get(0).getUuid(), teams.get(1).getUuid(), teams.get(2).getUuid(), teams.get(3).getUuid()))
			.teamBuildingUuid(teamBuilding.getUuid())
			.build());

		final User user2 = userRepository.save(User.builder()
			.name("넥터사람2")
			.position(Position.FRONT_END)
			.choices(List.of(teams.get(0).getUuid(), teams.get(1).getUuid(), teams.get(2).getUuid(), teams.get(3).getUuid()))
			.teamBuildingUuid(teamBuilding.getUuid())
			.build());

		sut.pickUsers(teamBuilding.getUuid(), teams.get(0).getUuid(), UserPickRequest.builder()
			.userUuids(List.of(user1.getUuid(), user2.getUuid()))
			.build());

		sut.pickUsers(teamBuilding.getUuid(), teams.get(1).getUuid(), UserPickRequest.builder()
			.userUuids(List.of())
			.build());

		sut.pickUsers(teamBuilding.getUuid(), teams.get(2).getUuid(), UserPickRequest.builder()
			.userUuids(List.of())
			.build());

		sut.pickUsers(teamBuilding.getUuid(), teams.get(3).getUuid(), UserPickRequest.builder()
			.userUuids(List.of())
			.build());

		assertThat(teamBuilding.getRoundStatus()).isEqualTo(RoundStatus.SECOND_ROUND);
	}
}
