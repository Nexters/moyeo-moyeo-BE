package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<User, Long> {

	List<User> findByUserUuidIn(List<String> userUuids);
}
