package com.nexters.moyeomoyeo.team_building.infra;

import com.nexters.moyeomoyeo.team_building.domain.entity.Room;
import com.nexters.moyeomoyeo.team_building.domain.repository.RoomRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

	private final RoomJpa jpa;

	@Override
	public Room save(Room room) {
		return jpa.save(room);
	}

	@Override
	public Optional<Room> findByRoomUuid(String roomUuid) {
		return jpa.findByRoomUuid(roomUuid);
	}
}
