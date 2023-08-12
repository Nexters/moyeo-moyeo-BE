package com.nexters.moyeomoyeo.team_building.domain.repository;

import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import java.util.Optional;

public interface TeamBuildingRepository {

	TeamBuilding save(TeamBuilding teamBuilding);

	Optional<TeamBuilding> findWithTeamsAndUsers(String uuid);

	Optional<TeamBuilding> findByUuid(String uuid);
}
