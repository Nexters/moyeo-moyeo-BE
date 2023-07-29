package com.nexters.moyeomoyeo.team_building.domain.repository;

import com.nexters.moyeomoyeo.team_building.domain.entity.Room;
import java.util.Optional;

public interface RoomRepository {

	Room save(Room room);

	Optional<Room> findByRoomUuid(String roomUuid);
}
