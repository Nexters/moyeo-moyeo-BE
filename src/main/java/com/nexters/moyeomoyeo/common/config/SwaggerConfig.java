package com.nexters.moyeomoyeo.common.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
	info = @Info(title = "모여모여 API 명세서", description = "넥스터즈 팀빌딩 서비스 API 명세서", version = "v1"),
	servers = @Server(url = "/"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi teamBuildingApi() {
		String[] paths = {"/api/**", "/notification/**"};

		return GroupedOpenApi.builder()
			.group("모여모여 API v1")
			.pathsToMatch(paths)
			.build();
	}
}
