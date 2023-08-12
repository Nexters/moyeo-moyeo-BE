package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.TeamBuilding;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamBuildingJpa extends JpaRepository<TeamBuilding, Long> {

	@Query("SELECT t FROM TeamBuilding t "
		+ " left outer join t.users u "
		+ " where t.uuid = :uuid ")
	Optional<TeamBuilding> findWithTeamsAndUsers(@Param("uuid") String uuid);

	Optional<TeamBuilding> findByUuid(String uuid);
}
