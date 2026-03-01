package com.github.scripting.programming.language.interview_sim_backend.entity;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "user_data", name = "question_tab")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column
    private Integer complexity;

    @Column(columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(name = "key_words", columnDefinition = "text[]")
    private List<String> keyWords;

    @ManyToMany(mappedBy = "questions")
    @ToString.Exclude
    private Set<Course> courses;
}
