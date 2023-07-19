package com.nexters.moyeomoyeo.team_building.domain.user;

import com.nexters.moyeomoyeo.team_building.domain.room.*;
import com.nexters.moyeomoyeo.team_building.domain.team.*;
import jakarta.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String name;
	@Enumerated(EnumType.STRING)
	private Position position;
	@Column(name = "first_choice")
	private String firstChoice;

	@Column(name = "second_choice")
	private String secondChoice;

	@Column(name = "third_choice")
	private String thirdChoice;

	@Column(name = "forth_choice")
	private String forthChoice;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_id")
	private Room room;
}
