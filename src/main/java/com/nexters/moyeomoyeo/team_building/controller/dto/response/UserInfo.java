package com.nexters.moyeomoyeo.team_building.controller.dto.response;

import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.domain.entity.UserChoice;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserInfo {

	@Schema(description = "회원 고유값")
	private String uuid;
	@Schema(description = "회원 이름")
	private String userName;
	@Schema(description = "회원 포지션")
	private Position position;
	@Schema(description = "회원이 선택한 팀들(배열 순서대로 1지망, 2지망, 3지망, 4지망)")
	private List<String> choices;
	@Schema(description = "최종 빌딩 된 팀")
	private String joinedTeamUuid;
	private String profileLink;
	@Builder.Default
	@Schema(description = "현재 팀 빌딩이 완료된 상태인지 여부")
	private boolean isSelectedTeam = false;

	public static UserInfo makeUserInfo(User user) {
		final List<String> choices = user.getChoices().stream()
			.map(UserChoice::getTeamUuid)
			.toList();

		final String joinedTeamUuid = Objects.isNull(user.getTeam()) ? null : user.getTeam().getUuid();

		return UserInfo.builder()
			.uuid(user.getUuid())
			.userName(user.getName())
			.position(user.getPosition())
			.choices(choices)
			.joinedTeamUuid(joinedTeamUuid)
			.isSelectedTeam(isSelectedTeam(choices, joinedTeamUuid))
			.build();
	}

	private static boolean isSelectedTeam(List<String> choices, String joinedTeamUuid) {
		if (CollectionUtils.isEmpty(choices)) {
			return false;
		}

		return choices.contains(joinedTeamUuid);
	}
}
