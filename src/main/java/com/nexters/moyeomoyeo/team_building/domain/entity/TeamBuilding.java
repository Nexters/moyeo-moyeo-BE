package com.nexters.moyeomoyeo.team_building.domain.entity;


import static com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus.FIRST_ROUND;

import com.nexters.moyeomoyeo.common.constant.ExceptionInfo;
import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import com.nexters.moyeomoyeo.common.util.UuidGenerator;
import com.nexters.moyeomoyeo.team_building.domain.constant.RoundStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class TeamBuilding extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(length = 30, unique = true)
	@Default
	private String uuid = UuidGenerator.createUuid();

	@Enumerated(EnumType.STRING)
	@Column(name = "round_status")
	@Builder.Default
	private RoundStatus roundStatus = FIRST_ROUND;

	@OneToMany(mappedBy = "teamBuilding", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Team> teams = new ArrayList<>();

	public RoundStatus nextRound() {
		if (this.roundStatus == RoundStatus.COMPLETE) {
			throw ExceptionInfo.COMPLETED_TEAM_BUILDING.exception();
		}

		this.roundStatus = this.roundStatus.getNextStatus();
		return this.roundStatus;
	}
}


