package com.nexters.moyeomoyeo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;


@MoyeoMoyeoTest
class MoyeoMoyeoApplicationTests {

	@Autowired
	private Environment environment;

	@Test
	void contextLoads() {
		assertEquals("test", environment.getActiveProfiles()[0]);
	}

}
