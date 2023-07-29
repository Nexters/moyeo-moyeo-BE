package com.nexters.moyeomoyeo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MoyeoMoyeoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoyeoMoyeoApplication.class, args);
	}

}
