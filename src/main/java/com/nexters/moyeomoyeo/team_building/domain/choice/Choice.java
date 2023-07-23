package com.nexters.moyeomoyeo.team_building.domain.choice;

import com.nexters.moyeomoyeo.team_building.domain.user.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Choice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "choice_id")
	private Long id;

	@Column(name = "choice_order")
	private Integer choiceOrder;

	@OneToOne(mappedBy = "user_uuid")
	private User user;

	public Choice(Long id, Integer choiceOrder, User user) {
		this.id = id;
		this.choiceOrder = choiceOrder;
		this.user = user;
	}
}
