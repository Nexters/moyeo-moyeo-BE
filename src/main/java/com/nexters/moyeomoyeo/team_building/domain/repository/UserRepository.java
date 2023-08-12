package com.nexters.moyeomoyeo.team_building.domain.repository;

import com.nexters.moyeomoyeo.team_building.domain.entity.User;

import java.util.Optional;

public interface UserRepository {

	User save(User user);

	void delete(User user);

	Optional<User> findByUuid(String uuid);
}
