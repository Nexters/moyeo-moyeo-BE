package com.nexters.moyeomoyeo.team_building.domain.entity;

import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import com.nexters.moyeomoyeo.common.util.UuidGenerator;
import com.nexters.moyeomoyeo.team_building.domain.constant.Position;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class Team extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private Long id;
	private String name;

	private String pmName;

	@Enumerated(EnumType.STRING)
	private Position pmPosition;

	@Column(name = "team_uuid", length = 30, unique = true)
	@Builder.Default
	private String teamUuid = UuidGenerator.createUuid();

	@Enumerated(EnumType.STRING)
	@Column(name = "round_status")
	private RoundStatus roundStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<User> users;

	protected Team(String name, String teamUuid, RoundStatus roundStatus, Room room) {
		this.name = name;
		this.teamUuid = teamUuid;
		this.roundStatus = roundStatus;
		this.room = room;
	}

	public static Team create(String name, String teamUuid, Room room) {
		return new Team(name, teamUuid, RoundStatus.FIRST_ROUND, room);
	}

	public void updateRoomStatus() {
		this.roundStatus = this.getRoundStatus().getNextStatus();
	}

}
