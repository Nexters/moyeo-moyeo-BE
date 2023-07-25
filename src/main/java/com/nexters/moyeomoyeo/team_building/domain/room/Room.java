package com.nexters.moyeomoyeo.team_building.domain.room;


import static com.nexters.moyeomoyeo.team_building.domain.team.RoundStatus.FIRST_ROUND;

import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import com.nexters.moyeomoyeo.common.util.UuidGenerator;
import com.nexters.moyeomoyeo.team_building.domain.team.RoundStatus;
import com.nexters.moyeomoyeo.team_building.domain.team.Team;
import com.nexters.moyeomoyeo.team_building.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Room extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;

	@Column(name = "room_uuid", length = 10, unique = true)
	@Default
	private String roomUuid = UuidGenerator.createUuid();

	@Column(name = "entrance_code")
	private String entranceCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "round_status")
	private RoundStatus roundStatus;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private List<Team> teams;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private List<User> users;

	protected Room(String roomUuid, String entranceCode, RoundStatus roundStatus, List<Team> teams, List<User> users) {
		this.roomUuid = roomUuid;
		this.entranceCode = entranceCode;
		this.roundStatus = roundStatus;
		this.teams = teams;
		this.users = users;
	}

	public static Room create(String roomUuid, String entranceCode, List<Team> teams, List<User> users) {
		return new Room(roomUuid, entranceCode, FIRST_ROUND, teams, users);
	}

	public void addUsers(List<User> users) {
		this.users.addAll(users);
	}

	public void addTeams(List<Team> teams) {
		this.teams.addAll(teams);
	}

	public void changeRoomStatus(RoundStatus updatedRoomStatus) {
		this.roundStatus = updatedRoomStatus;
	}
}


