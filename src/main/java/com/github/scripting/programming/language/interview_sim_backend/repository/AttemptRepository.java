package com.github.scripting.programming.language.interview_sim_backend.repository;

import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
}
