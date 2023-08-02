package com.nexters.moyeomoyeo.team_building.service;

import com.nexters.moyeomoyeo.team_building.domain.entity.*;
import com.nexters.moyeomoyeo.team_building.domain.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class ChoiceService {

	private final ChoiceRepository choiceRepository;

	public UserChoice createUserChoice(Integer choiceOrder, User user, Team team) {
		UserChoice userChoice = UserChoice.create(choiceOrder, user, team);
		return choiceRepository.save(userChoice);
	}
}
