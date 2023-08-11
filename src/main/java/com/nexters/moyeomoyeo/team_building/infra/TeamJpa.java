package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamJpa extends JpaRepository<Team, Long> {

	Optional<Team> findByUuid(String uuid);

	@Query("SELECT t FROM Team t join fetch t.users u where t.uuid =: uuid ")
	Optional<Team> findWithUsers(@Param("uuid") String uuid);
}
