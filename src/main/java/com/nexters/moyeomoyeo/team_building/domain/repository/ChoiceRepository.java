package com.nexters.moyeomoyeo.team_building.domain.repository;

import com.nexters.moyeomoyeo.team_building.domain.entity.*;

import java.util.*;

public interface ChoiceRepository {
	UserChoice save(UserChoice choice);

	Optional<UserChoice> findUserChoiceByUserAndChoiceOrder(String userUuId, Integer choiceOrder);
}
