package com.github.scripting.programming.language.interview_sim_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(schema = "user_data", name = "user_tab")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    @ToString.Exclude
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    @Column
    private LocalDate birthdate;

    @Column(updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;
}
