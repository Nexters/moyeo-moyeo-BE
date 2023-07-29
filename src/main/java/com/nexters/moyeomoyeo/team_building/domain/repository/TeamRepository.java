package com.nexters.moyeomoyeo.team_building.domain.repository;

import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import java.util.Optional;

public interface TeamRepository {

	Optional<Team> findByTeamUuid(String teamUuid);
}
