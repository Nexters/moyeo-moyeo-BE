package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<User, Long> {

	Optional<User> findByUuid(String uuid);

	void delete(User user);

	List<User> findByUuidIn(List<String> uuids);

	List<User> findByTeamBuildingId(Long teamBuildingId);
}
