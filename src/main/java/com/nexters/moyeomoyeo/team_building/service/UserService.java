package com.nexters.moyeomoyeo.team_building.service;

import static com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo.makeUserInfo;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.UserRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.domain.entity.UserChoice;
import com.nexters.moyeomoyeo.team_building.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final TeamBuildingService teamBuildingService;
	private final TeamService teamService;
	private final UserRepository userRepository;

	@Transactional
	public UserInfo createUser(String teamBuildingUuid, UserRequest request) {
		final User user = User.builder()
			.name(request.getName())
			.position(request.getPosition())
			.profileLink(request.getProfileLink())
			.build();

		final List<UserChoice> choices = createUserChoices(request.getChoices());
		for (final UserChoice choice : choices) {
			choice.addUser(user);
		}

		user.addTeamBuilding(teamBuildingService.findByUuid(teamBuildingUuid));

		return makeUserInfo(userRepository.save(user));
	}

	private List<UserChoice> createUserChoices(List<String> teamUuids) {
		final List<UserChoice> choices = new ArrayList<>();
		int choiceOrder = 1;

		for (final String teamUuid : teamUuids) {
			final UserChoice userChoice = UserChoice.builder()
				.choiceOrder(choiceOrder++)
				.teamUuid(teamUuid)
				.build();

			choices.add(userChoice);
		}
		return choices;
	}

	@Transactional
	public UserInfo adjustUser(String userUuid, String teamUuid) {
		final User user = userRepository.findByUuid(userUuid).orElseThrow(ExceptionInfo.INVALID_USER_UUID::exception);

		user.adjustTeam(teamService.findByUuid(teamUuid).orElse(null));

		return makeUserInfo(user);
	}
}
