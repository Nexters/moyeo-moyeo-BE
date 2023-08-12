package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import com.nexters.moyeomoyeo.team_building.domain.repository.TeamBuildingRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TeamBuildingRepositoryImpl implements TeamBuildingRepository {

	private final TeamBuildingJpa jpa;

	@Override
	public TeamBuilding save(TeamBuilding teamBuilding) {
		return jpa.save(teamBuilding);
	}

	@Override
	public Optional<TeamBuilding> findWithTeamsAndUsers(String uuid) {
		return jpa.findWithTeamsAndUsers(uuid);
	}

	@Override
	public Optional<TeamBuilding> findByUuid(String uuid) {
		return jpa.findByUuid(uuid);
	}
}
