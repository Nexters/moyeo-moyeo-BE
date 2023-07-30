package com.nexters.moyeomoyeo.team_building.domain.entity;


import static com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus.FIRST_ROUND;

import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import com.nexters.moyeomoyeo.common.util.UuidGenerator;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.*;

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

	@Column(name = "room_name")
	private String name;

	@Column(name = "room_uuid", length = 30, unique = true)
	@Default
	private String roomUuid = UuidGenerator.createUuid();

	@Enumerated(EnumType.STRING)
	@Column(name = "round_status")
	private RoundStatus roundStatus;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private List<Team> teams;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<User> users;

	protected Room(String name, RoundStatus roundStatus) {
		this.name = name;
		this.roundStatus = roundStatus;
		this.teams = new ArrayList<>();
		this.users = new ArrayList<>();
	}

	public static Room create(String roomName) {
		return new Room(roomName, FIRST_ROUND);
	}

	public void addTeams(List<Team> teams) {
		this.teams.addAll(teams);
	}

	public void addUsers(List<User> users) {
		this.users.addAll(users);
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public void updateRoomStatus() {
		this.roundStatus = this.getRoundStatus().getNextStatus();
	}
}


