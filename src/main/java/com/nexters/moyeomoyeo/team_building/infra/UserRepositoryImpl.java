package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import com.nexters.moyeomoyeo.team_building.domain.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpa jpa;

	@Override
	public User save(User user) {
		return jpa.save(user);
	}

	@Override
	public List<User> findByUuidIn(List<String> userUuids) {
		return jpa.findByUserUuidIn(userUuids);
	}
}
