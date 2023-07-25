package com.nexters.moyeomoyeo.team_building.domain.choice;

import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import com.nexters.moyeomoyeo.team_building.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class Choice extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "choice_id")
	private Long id;

	@Column(name = "choice_order")
	private Integer choiceOrder;

	@OneToOne(mappedBy = "choice")
	private User user;

	protected Choice(Integer choiceOrder, User user) {
		this.choiceOrder = choiceOrder;
		this.user = user;
	}
}
