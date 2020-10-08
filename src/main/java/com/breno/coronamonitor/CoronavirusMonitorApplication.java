package com.breno.coronamonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Ativa o Schaduler da entity services
public class CoronavirusMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronavirusMonitorApplication.class, args);
	}

}
