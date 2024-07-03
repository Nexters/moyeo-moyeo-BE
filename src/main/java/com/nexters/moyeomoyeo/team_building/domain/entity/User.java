package com.nexters.moyeomoyeo.team_building.domain.entity;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import com.nexters.moyeomoyeo.common.util.UuidGenerator;
import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

	public static final int MAX_CHOICE_SIZE = 4;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30, unique = true)
	@Builder.Default
	private String uuid = UuidGenerator.createUuid();

	private String name;

	@Enumerated(EnumType.STRING)
	private Position position;

	private String profileLink;

	@Type(JsonType.class)
	@Column(columnDefinition = "TEXT")
	@Builder.Default
	private List<String> choices = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	private String teamBuildingUuid;

	@Enumerated(EnumType.STRING)
	private RoundStatus selectedRound;

	public void addTeam(Team team) {
		if (this.team != null) {
			throw ExceptionInfo.ALREADY_JOINED_USER.exception();
		}
		this.team = team;
		this.team.getUsers().add(this);
	}


	/**
	 * @param order 순서
	 * @return 순서에 해당하는 teamUuid
	 */
	public String findChoice(int order) {
		return this.choices.get(order - 1);
	}


	/**
	 * 선택한 팀이 없을 때 예외 처리 기존 팀이 있다면 지우고 선택해주기
	 *
	 * @param team
	 */
	public void adjustTeam(@Nullable Team team) {
		if (!Objects.isNull(this.team)) {
			this.team.getUsers().remove(this);
		}

		this.team = team;

		if (!Objects.isNull(team)) {
			team.getUsers().add(this);
		}
	}

	public void updateSelectedRound(RoundStatus round) {
		this.selectedRound = round;
	}
}


