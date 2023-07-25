package com.nexters.moyeomoyeo.team_building.domain.user;

import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import com.nexters.moyeomoyeo.common.util.UuidGenerator;
import com.nexters.moyeomoyeo.team_building.domain.choice.Choice;
import com.nexters.moyeomoyeo.team_building.domain.room.Room;
import com.nexters.moyeomoyeo.team_building.domain.team.Team;
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
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_uuid", length = 10, unique = true)
	@Builder.Default
	private String userUuid = UuidGenerator.createUuid();

	private String name;

	@Enumerated(EnumType.STRING)
	private Position position;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "choice_id")
	private Choice choice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_uuid")
	private Team team;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_uuid")
	private Room room;

	protected User(String userUuid, String name, Position position, Choice choice, Team team, Room room) {
		this.userUuid = userUuid;
		this.name = name;
		this.position = position;
		this.choice = choice;
		this.team = team;
		this.room = room;
	}
}


