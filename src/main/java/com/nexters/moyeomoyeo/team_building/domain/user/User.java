package com.nexters.moyeomoyeo.team_building.domain.user;

import com.nexters.moyeomoyeo.team_building.domain.choice.*;
import com.nexters.moyeomoyeo.team_building.domain.room.*;
import com.nexters.moyeomoyeo.team_building.domain.team.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_uuid", length = 10, unique = true)
	private String userUuid;

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

	public User(Long id, String userUuid, String name, Position position, Choice choice, Team team, Room room) {
		this.id = id;
		this.userUuid = userUuid;
		this.name = name;
		this.position = position;
		this.choice = choice;
		this.team = team;
		this.room = room;
	}
}


