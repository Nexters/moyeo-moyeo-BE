package com.nexters.moyeomoyeo.team_building.domain.entity;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
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
import java.util.ArrayList;
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
	private Long id;
	private String name;

	private String pmName;

	@Enumerated(EnumType.STRING)
	private Position pmPosition;

	@Column(length = 30, unique = true)
	@Builder.Default
	private String uuid = UuidGenerator.createUuid();

	@Enumerated(EnumType.STRING)
	@Column(name = "round_status")
	@Builder.Default
	private RoundStatus roundStatus = RoundStatus.START;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_building_id")
	private TeamBuilding teamBuilding;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<User> users = new ArrayList<>();

	public void nextRound() {
		if (this.roundStatus == RoundStatus.COMPLETE) {
			throw ExceptionInfo.COMPLETED_TEAM_BUILDING.exception();
		}

		this.roundStatus = this.roundStatus.getNextStatus();
	}

	public void addTeamBuilding(TeamBuilding teamBuilding) {
		this.teamBuilding = teamBuilding;
		this.teamBuilding.getTeams().add(this);
	}
}
