package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface ChoiceJpa extends JpaRepository<UserChoice, Long> {
	@Query("SELECT c FROM UserChoice c join fetch c.user where c.user.userUuid =: userUuid and c.choiceOrder =: choiceOrder ")
	Optional<UserChoice> findUserChoiceByUserAndChoiceOrder(@Param("userUuid") String userUuid, @Param("choiceOrder") Integer choiceOrder);
}
