package com.nexters.moyeomoyeo.team_building.domain.room;

import com.nexters.moyeomoyeo.team_building.domain.team.*;
import com.nexters.moyeomoyeo.team_building.domain.user.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import static com.nexters.moyeomoyeo.team_building.domain.room.RoomStatus.FIRST_ROUND;

@Entity
@Getter
@NoArgsConstructor
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;

	@Column(name = "room_uuid", length = 10, unique = true)
	private String roomUuid;

	@Column(name = "entrance_code")
	private String entranceCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "round_status")
	private RoomStatus roomStatus;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private List<Team> teams = new ArrayList<Team>();

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private List<User> users = new ArrayList<User>();

	public Room(Long id, String roomUuid, String entranceCode, RoomStatus roomStatus, List<Team> teams, List<User> users) {
		this.id = id;
		this.roomUuid = roomUuid;
		this.entranceCode = entranceCode;
		this.roomStatus = roomStatus;
		this.teams = teams;
		this.users = users;
	}

	public Room(Long id, String roomUuid, String entranceCode, List<Team> teams, List<User> users) {
		this(id, roomUuid, entranceCode, FIRST_ROUND, teams, users);
	}

	public void addUsers(List<User> users) {
		users.addAll(users);
	}

	public void addTeams(List<Team> teams) {
		teams.addAll(teams);
	}

	public void changeRoomStatus(RoomStatus updatedRoomStatus) {
		this.roomStatus = updatedRoomStatus;
	}
}


