package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.Room;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomJpa extends JpaRepository<Room, Long> {

	@Query("SELECT r FROM Room r "
		+ " left outer join r.users u "
		+ " where r.roomUuid = :roomUuid ")
	Optional<Room> findByRoomUuid(@Param("roomUuid") String roomUuid);
}
