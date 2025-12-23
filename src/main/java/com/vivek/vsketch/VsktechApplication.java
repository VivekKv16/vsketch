package com.vivek.vsketch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class VsktechApplication {

	public static void main(String[] args) {
		SpringApplication.run(VsktechApplication.class, args);
	}

}
