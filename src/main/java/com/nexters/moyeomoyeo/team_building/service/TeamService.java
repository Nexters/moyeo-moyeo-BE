package com.nexters.moyeomoyeo.team_building.service;


import com.nexters.moyeomoyeo.team_building.domain.entity.Team;
import com.nexters.moyeomoyeo.team_building.domain.repository.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final TeamRepository teamRepository;

	public Optional<Team> findByUuid(String teamUuid) {
		return teamRepository.findByUuid(teamUuid);
	}
}
