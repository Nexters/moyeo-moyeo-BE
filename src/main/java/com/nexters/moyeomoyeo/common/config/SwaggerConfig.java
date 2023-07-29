package com.nexters.moyeomoyeo.common.config;


import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;
import lombok.*;
import org.springdoc.core.models.*;
import org.springframework.context.annotation.*;

@OpenAPIDefinition(
	info = @Info(title = "모여모여 API 명세서",
		description = "넥스터즈 팀빌딩 서비스 API 명세서",
		version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi chatOpenApi() {
		String[] paths = {"/v1/**"};

		return GroupedOpenApi.builder()
			.group("모여모여 API v1")
			.pathsToMatch(paths)
			.build();
	}
}
