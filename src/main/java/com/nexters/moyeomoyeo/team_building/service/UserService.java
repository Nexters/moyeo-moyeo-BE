package com.nexters.moyeomoyeo.team_building.service;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.notification.service.NotificationService;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.UserRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.domain.entity.UserChoice;
import com.nexters.moyeomoyeo.team_building.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo.makeUserInfo;

@Service
@RequiredArgsConstructor
public class UserService {

	private final TeamBuildingService teamBuildingService;
	private final NotificationService notificationService;
	private final TeamService teamService;
	private final UserRepository userRepository;

	@Transactional
	public UserInfo createUser(String teamBuildingUuid, UserRequest request) {
		final User user = makeUser(request);

		final List<UserChoice> choices = createUserChoices(request.getChoices());
		for (final UserChoice choice : choices) {
			choice.addUser(user);
		}

		user.addTeamBuilding(teamBuildingService.findByUuid(teamBuildingUuid));
		UserInfo userInfo = makeUserInfo(userRepository.save(user));
		notificationService.broadCast(teamBuildingUuid, "create-user", userInfo);

		return userInfo;
	}

	@Transactional
	public UserInfo adjustUser(String teamBuildingUuid, String userUuid, String teamUuid) {
		final User user = findUser(userUuid);

		user.adjustTeam(teamService.findByUuid(teamUuid).orElse(null));
		UserInfo userInfo = makeUserInfo(user);

		notificationService.broadCast(teamBuildingUuid, "adjust-user", userInfo);
		return userInfo;
	}

	private User findUser(String userUuid) {
		return userRepository.findByUuid(userUuid).orElseThrow(ExceptionInfo.INVALID_USER_UUID::exception);
	}

	private static User makeUser(UserRequest request) {
		final User user = User.builder()
			.name(request.getName())
			.position(request.getPosition())
			.profileLink(request.getProfileLink())
			.build();
		return user;
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
	public void deleteUser(String teamBuildingUuid, String userUuid) {
		final User targetUser = findUser(userUuid);

		userRepository.delete(targetUser);
		notificationService.broadCast(teamBuildingUuid, "delete-user", userUuid);


	}
}
