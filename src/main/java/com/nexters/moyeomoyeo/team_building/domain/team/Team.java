package com.nexters.moyeomoyeo.team_building.domain.team;

import com.nexters.moyeomoyeo.team_building.domain.room.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private Long id;
	private String name;

	@Column(name = "team_uuid")
	private String uuid;

	@Enumerated(EnumType.STRING)
	@Column(name = "round_status")
	private RoundStatus roundStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	public Team(Long id, String name, String uuid, RoundStatus roundStatus, Room room) {
		this.id = id;
		this.name = name;
		this.uuid = uuid;
		this.roundStatus = roundStatus;
		this.room = room;
	}

	public Team(Long id, String name, String uuid, Room room) {
		this(id, name, uuid, RoundStatus.FIRST_ROUND, room);
	}

	public void changeRoomStatus(RoundStatus updateRoundStatus) {
		this.roundStatus = updateRoundStatus;
	}

	public boolean addRoom(Room room) {
		if (this.room == null) {
			this.room = room;
			return true;
		}
		return false;
	}
}
