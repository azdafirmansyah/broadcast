package com.azda.broadcast;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BroadcastApplication {

	private static final Logger logger = LogManager.getLogger(BroadcastApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(BroadcastApplication.class, args);
	}

}
