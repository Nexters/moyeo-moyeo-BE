package com.nexters.moyeomoyeo.team_building.domain.repository;

import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

	User save(User user);

	void delete(User user);

	Optional<User> findByUuid(String uuid);

	List<User> findByUuidIn(List<String> uuids);

	List<User> findByTeamBuildingUuid(String teamBuildingUuid);
}
