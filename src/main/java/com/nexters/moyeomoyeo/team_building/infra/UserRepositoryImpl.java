package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpa jpa;

	@Override
	public User save(User user) {
		return jpa.save(user);
	}

	@Override
	public void delete(User user) {
		jpa.delete(user);
	}

	@Override
	public Optional<User> findByUuid(String uuid) {
		return jpa.findByUuid(uuid);
	}
}
