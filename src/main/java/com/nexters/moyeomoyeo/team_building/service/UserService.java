package com.nexters.moyeomoyeo.team_building.service;

import com.nexters.moyeomoyeo.team_building.controller.dto.*;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.*;
import com.nexters.moyeomoyeo.team_building.domain.entity.*;
import com.nexters.moyeomoyeo.team_building.domain.repository.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final RoomService roomService;
	private final UserRepository userRepository;
	private final ChoiceService choiceService;
	private final TeamService teamService;

	@Transactional
	public UserInfo createUser(String roomUuid, UserCreateRequest createRequest) {
		Room targetRoom = roomService.findByRoomUuid(roomUuid);
		User savedUser = userRepository.save(toUser(createRequest, targetRoom));

		savedUser.addChoices(createUserChoices(createRequest, savedUser));

		return makeUserInfo(savedUser);
	}

	private static User toUser(UserCreateRequest createRequest, Room targetRoom) {
		return User.create(createRequest.getName(), createRequest.getPosition(), targetRoom);
	}

	private List<UserChoice> createUserChoices(UserCreateRequest createRequest, User targetUser) {
		List<UserChoice> choices = new ArrayList<>();
		int choiceOrder = 1;

		for (String choice : createRequest.getChoices()) {
			Team choicedTeam = teamService.findByTeamUuid(choice);
			UserChoice userChoice = choiceService.createUserChoice(choiceOrder++, targetUser, choicedTeam);

			choices.add(userChoice);
		}
		return choices;
	}

	public static UserInfo makeUserInfo(User user) {
		final List<String> choices = user.getChoices().stream()
			.map(userChoice -> userChoice.getTeam().getTeamUuid())
			.toList();

		final String joinedTeamUuid = Objects.isNull(user.getTeam()) ? null : user.getTeam().getTeamUuid();

		return UserInfo.builder()
			.uuid(user.getUserUuid())
			.userName(user.getName())
			.position(user.getPosition())
			.choices(choices)
			.joinedTeamUuid(joinedTeamUuid)
			.isSelectedTeam(isSelectedTeam(choices, joinedTeamUuid))
			.build();
	}

	public static boolean isSelectedTeam(List<String> choices, String joinedTeamUuid) {
		if (CollectionUtils.isEmpty(choices)) {
			return false;
		}

		return choices.contains(joinedTeamUuid);
	}
}
