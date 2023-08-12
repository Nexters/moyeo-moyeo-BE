package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpa extends JpaRepository<User, Long> {

	Optional<User> findByUuid(String uuid);

	void delete(User user);
}
