package com.nexters.moyeomoyeo.team_building.service;

import static com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo.makeUserInfo;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.notification.service.NotificationService;
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

	private final NotificationService notificationService;
	private final UserRepository userRepository;

	private static User makeUser(UserRequest request) {
		return User.builder()
			.name(request.getName())
			.position(request.getPosition())
			.profileLink(request.getProfileLink())
			.build();
	}

	private static boolean isValidUser(List<String> userUuids, List<User> pickedUsers) {
		return pickedUsers.size() == userUuids.size();
	}

	@Transactional
	public UserInfo createUser(String teamBuildingUuid, UserRequest request) {
		final User user = makeUser(request);

		final List<UserChoice> choices = createUserChoices(request.getChoices());
		for (final UserChoice choice : choices) {
			choice.addUser(user);
		}

		UserInfo userInfo = makeUserInfo(userRepository.save(user));
		notificationService.broadCast(teamBuildingUuid, "create-user", userInfo);

		return userInfo;
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
		final User targetUser = findByUuid(userUuid);

		userRepository.delete(targetUser);
		notificationService.broadCast(teamBuildingUuid, "delete-user", userUuid);


	}

	public User findByUuid(String userUuid) {
		return userRepository.findByUuid(userUuid).orElseThrow(ExceptionInfo.INVALID_USER_UUID::exception);
	}

	public List<User> findByUuidIn(List<String> uuids) {
		final List<User> users = userRepository.findByUuidIn(uuids);
		if (!isValidUser(uuids, users)) {
			throw ExceptionInfo.INVALID_USER_UUID.exception();
		}

		return users;
	}

	public List<User> findByTeamBuildingId(Long teamBuildingId) {
		return userRepository.findByTeamBuildingId(teamBuildingId);
	}
}
