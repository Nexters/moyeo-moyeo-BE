package com.nexters.moyeomoyeo.team_building.domain.repository;

import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import java.util.Optional;

public interface TeamRepository {

	Optional<Team> findByUuid(String uuid);

	Optional<Team> findWithUsers(String uuid);

}
