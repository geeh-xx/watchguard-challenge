package com.watchguard.challange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WatchGuardApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchGuardApplication.class, args);
	}

}
