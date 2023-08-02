package com.nexters.moyeomoyeo.team_building.domain.entity;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import com.nexters.moyeomoyeo.common.util.UuidGenerator;
import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
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
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

	public static final int MAX_CHOICE_SIZE = 5;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_uuid", length = 30, unique = true)
	@Builder.Default
	private String userUuid = UuidGenerator.createUuid();

	private String name;

	@Enumerated(EnumType.STRING)
	private Position position;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("choice_order asc")
	private List<UserChoice> choices;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	protected User(String name, Position position, Room room) {
		this.name = name;
		this.position = position;
		this.room = room;
	}

	public static User create(String name, Position position, Room room) {
		return new User(name, position, room);
	}

	public void addChoices(List<UserChoice> choices) {
		verifyChoiceSize(choices);
		this.choices = choices;
	}

	private void verifyChoiceSize(List<UserChoice> choices) {
		if (choices.size() > MAX_CHOICE_SIZE) {
			throw ExceptionInfo.INVALID_SIZE_USER_CHOICE_PICK.exception();
		}
	}

	public void addTeam(Team team) {
		if (this.team != null) {
			throw ExceptionInfo.ALREADY_JOINED_USER.exception();
		}
		this.team = team;
		this.team.getUsers().add(this);
	}

	public UserChoice findChoiceTeam(int weight) {
		return this.choices.get(weight - 1);
	}
}


