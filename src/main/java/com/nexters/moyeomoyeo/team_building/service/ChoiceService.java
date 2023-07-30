package com.nexters.moyeomoyeo.team_building.service;

import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.domain.entity.*;
import com.nexters.moyeomoyeo.team_building.domain.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

import static com.nexters.moyeomoyeo.team_building.service.TeamService.makeTeamInfo;
import static com.nexters.moyeomoyeo.team_building.service.UserService.makeUserInfo;

@Service
@RequiredArgsConstructor
public class ChoiceService {

	private final ChoiceRepository choiceRepository;

	public UserChoiceInfoResponse createUserChoiceToUserChoiceInfo(Integer choiceOrder, User user, Team team) {
		UserChoice userChoice = UserChoice.create(choiceOrder, user, team);
		UserChoice saved = choiceRepository.save(userChoice);
		return makeChoiceInfo(saved);
	}

	public UserChoice createUserChoice(Integer choiceOrder, User user, Team team) {
		UserChoice userChoice = UserChoice.create(choiceOrder, user, team);
		return choiceRepository.save(userChoice);
	}

	public static UserChoiceInfoResponse makeChoiceInfo(UserChoice saved) {
		return UserChoiceInfoResponse.builder()
			.id(saved.getId())
			.choiceOrder(saved.getChoiceOrder())
			.team(makeTeamInfo(saved.getTeam()))
			.user(makeUserInfo(saved.getUser()))
			.build();
	}
}
