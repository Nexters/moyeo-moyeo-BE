package com.nexters.moyeomoyeo.team_building.domain.entity;

import com.nexters.moyeomoyeo.common.constant.*;
import com.nexters.moyeomoyeo.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class UserChoice extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "choice_id")
	private Long id;

	@Column(name = "choice_order")
	private Integer choiceOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;


	protected UserChoice(Integer choiceOrder, User user, Team team) {
		this.choiceOrder = choiceOrder;
		this.user = user;
		this.team = team;
	}

	public static UserChoice create(Integer choiceOrder, User user, Team team) {
		verifyChoiceOrder(choiceOrder);
		return new UserChoice(choiceOrder, user, team);
	}

	private static void verifyChoiceOrder(int choiceOrder) {
		if (choiceOrder < 0 || choiceOrder > 5) {
			throw ExceptionInfo.INVALID_USER_CHOICE_ORDER.exception();
		}
	}
}
