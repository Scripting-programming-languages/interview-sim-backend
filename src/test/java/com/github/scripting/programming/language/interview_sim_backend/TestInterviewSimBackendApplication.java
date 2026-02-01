package com.github.scripting.programming.language.interview_sim_backend;

import org.springframework.boot.SpringApplication;

public class TestInterviewSimBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(InterviewSimBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
