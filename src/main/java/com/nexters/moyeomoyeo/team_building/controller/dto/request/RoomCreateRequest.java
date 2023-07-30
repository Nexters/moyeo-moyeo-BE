package com.nexters.moyeomoyeo.team_building.controller.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoomCreateRequest {
	private String name;
}
