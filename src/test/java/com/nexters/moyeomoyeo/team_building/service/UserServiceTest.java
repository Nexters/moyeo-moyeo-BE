package com.nexters.moyeomoyeo.team_building.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.nexters.moyeomoyeo.MoyeoMoyeoTest;
import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.common.exception.vo.CustomException;
import com.nexters.moyeomoyeo.team_building.controller.dto.request.UserRequest;
import com.nexters.moyeomoyeo.team_building.controller.dto.response.UserInfo;
import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MoyeoMoyeoTest
class UserServiceTest {

	@Autowired
	private UserService sut;

	@Test
	void createUser() {
		final UserInfo userInfo = sut.createUser("team-building-uuid", UserRequest.builder()
				.name("넥터사람")
				.position(Position.BACK_END)
				.choices(List.of("1", "2", "3", "4"))
				.build());

		assertThat(userInfo.getUserName()).isEqualTo("넥터사람");
		assertThat(userInfo.getPosition()).isEqualTo(Position.BACK_END);
		assertThat(userInfo.getChoices().get(0)).isEqualTo("1");
	}

	@Test
	void deleteUser() {
		final UserInfo createdUser = sut.createUser("team-building-uuid", UserRequest.builder()
			.name("넥터사람")
			.position(Position.BACK_END)
			.choices(List.of("1", "2", "3", "4"))
			.build());

		sut.deleteUser("team-building-uuid", createdUser.getUuid());

		assertThatThrownBy(() -> sut.findByUuid(createdUser.getUuid()))
			.isInstanceOf(CustomException.class).hasMessage(ExceptionInfo.INVALID_USER_UUID.getMessage());
	}
}
