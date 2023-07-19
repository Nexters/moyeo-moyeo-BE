package com.nexters.moyeomoyeo.team_building.domain.room;

import com.nexters.moyeomoyeo.team_building.domain.team.*;
import com.nexters.moyeomoyeo.team_building.domain.user.*;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;
	private String name;

	private String entrance_uri;

	@Enumerated(EnumType.STRING)
	@Column(name = "round_status")
	private RoomStatus RoomStatus;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private List<Team> teams;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private List<User> users;
}


