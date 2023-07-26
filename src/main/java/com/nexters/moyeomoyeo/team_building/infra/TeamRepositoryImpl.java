package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import com.nexters.moyeomoyeo.team_building.domain.repository.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

	private final TeamJpa jpa;

	@Override
	public Optional<Team> findByTeamUuid(String teamUuid) {
		return jpa.findByTeamUuid(teamUuid);
	}
}
