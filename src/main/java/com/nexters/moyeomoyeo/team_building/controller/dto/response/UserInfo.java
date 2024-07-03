package com.nexters.moyeomoyeo.team_building.controller.dto.response;

import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	@Schema(description = "팀원 추가 링크")
	private String profileLink;
	@Schema(description = "선택받은 라운드")
	private RoundStatus selectedRound;


	public static UserInfo makeUserInfo(User user) {
		final String joinedTeamUuid = Objects.isNull(user.getTeam()) ? null : user.getTeam().getUuid();

		return UserInfo.builder()
			.uuid(user.getUuid())
			.userName(user.getName())
			.position(user.getPosition())
			.choices(user.getChoices())
			.joinedTeamUuid(joinedTeamUuid)
			.profileLink(user.getProfileLink())
			.selectedRound(user.getSelectedRound())
			.build();
	}
}
