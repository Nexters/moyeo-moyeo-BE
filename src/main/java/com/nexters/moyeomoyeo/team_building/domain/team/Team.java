package com.nexters.moyeomoyeo.team_building.domain.team;

import com.nexters.moyeomoyeo.team_building.domain.room.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private Long id;
	private String name;

	@Column(name = "team_status")
	private String uuid;

	@Enumerated(EnumType.STRING)
	@Column(name = "round_status")
	private RoundStatus roundStatus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_id")
	private Room room;
}
