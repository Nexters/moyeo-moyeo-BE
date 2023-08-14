package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamBuildingJpa extends JpaRepository<TeamBuilding, Long> {

	Optional<TeamBuilding> findByUuid(String uuid);
}
