package com.github.scripting.programming.language.interview_sim_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(schema = "user_data", name = "category_tab")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 5000)
    private String description;

    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    private Set<Course> courses;
}
