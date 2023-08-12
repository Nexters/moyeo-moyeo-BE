package com.nexters.moyeomoyeo.team_building.domain.entity;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import com.nexters.moyeomoyeo.common.util.UuidGenerator;
import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
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

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

	public static final int MAX_CHOICE_SIZE = 4;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "uuid", length = 30, unique = true)
	@Builder.Default
	private String uuid = UuidGenerator.createUuid();

	private String name;

	@Enumerated(EnumType.STRING)
	private Position position;

	private String profileLink;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("choice_order asc")
	@Builder.Default
	private List<UserChoice> choices = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_building_id")
	private TeamBuilding teamBuilding;

	protected User(String name, Position position, TeamBuilding teamBuilding) {
		this.name = name;
		this.position = position;
		this.teamBuilding = teamBuilding;
		this.uuid = UuidGenerator.createUuid();
	}

	public void addTeam(Team team) {
		if (this.team != null) {
			throw ExceptionInfo.ALREADY_JOINED_USER.exception();
		}
		this.team = team;
		this.team.getUsers().add(this);
	}

	public void addTeamBuilding(TeamBuilding teamBuilding) {
		this.teamBuilding = teamBuilding;
		this.teamBuilding.getUsers().add(this);
	}

	public UserChoice findChoice(int weight) {
		return this.choices.get(weight - 1);
	}

	public void adjustTeam(@Nullable Team team) {
		if (!Objects.isNull(this.team)) {
			this.team.getUsers().remove(this);
		}

		this.team = team;

		if (!Objects.isNull(team)) {
			team.getUsers().add(this);
		}
	}
}


