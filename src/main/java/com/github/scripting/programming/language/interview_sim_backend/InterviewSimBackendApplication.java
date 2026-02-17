package com.github.scripting.programming.language.interview_sim_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class InterviewSimBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewSimBackendApplication.class, args);
	}

}
