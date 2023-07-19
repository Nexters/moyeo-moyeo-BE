package com.nexters.moyeomoyeo.team_building.domain.room;

import jakarta.persistence.*;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;
	private String name;

	private String entrance_uri;

	@Enumerated(EnumType.STRING)
	private RoomStatus roomStatus;
}


