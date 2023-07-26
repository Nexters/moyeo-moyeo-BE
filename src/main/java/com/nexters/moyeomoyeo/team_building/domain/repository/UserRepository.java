package com.nexters.moyeomoyeo.team_building.domain.repository;

import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import java.util.List;

public interface UserRepository {

	User save(User user);

	List<User> findByUuidIn(List<String> uuids);
}
