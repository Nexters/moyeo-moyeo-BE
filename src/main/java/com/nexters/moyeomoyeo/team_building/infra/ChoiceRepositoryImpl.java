package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.*;
import com.nexters.moyeomoyeo.team_building.domain.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ChoiceRepositoryImpl implements ChoiceRepository {
	private final ChoiceJpa jpa;

	@Override
	public UserChoice save(UserChoice choice) {
		return jpa.save(choice);
	}

	@Override
	public Optional<UserChoice> findUserChoiceByUserAndChoiceOrder(String userUuid, Integer choiceOrder) {
		return jpa.findUserChoiceByUserAndChoiceOrder(userUuid, choiceOrder);
	}
}
