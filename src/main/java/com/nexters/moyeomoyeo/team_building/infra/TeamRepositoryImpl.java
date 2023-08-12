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
	public Optional<Team> findByUuid(String uuid) {
		return jpa.findByUuid(uuid);
	}

	@Override
	public Optional<Team> findWithUsers(String uuid) {
		return jpa.findWithUsers(uuid);
	}
}
