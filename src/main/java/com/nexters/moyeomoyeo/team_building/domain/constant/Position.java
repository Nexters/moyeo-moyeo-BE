package com.nexters.moyeomoyeo.team_building.domain.constant;

import lombok.*;

/**
 *  serialize/deserialize 할 때 jsonValue 사용해서 enum mapping 되도록 설정
 */
@Getter
@RequiredArgsConstructor
public enum Position implements Constant {
	DESIGNER("designer"),
	BACK_END("backend"),
	FRONT_END("frontend"),
	IOS("ios"),
	ANDROID("android");

	private final String value;
}
