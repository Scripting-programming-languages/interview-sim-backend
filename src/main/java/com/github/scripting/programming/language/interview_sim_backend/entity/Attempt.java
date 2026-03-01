package com.github.scripting.programming.language.interview_sim_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(schema = "user_data", name = "attempt_tab")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @ToString.Exclude
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AttemptStatus status = AttemptStatus.IN_PROGRESS;

    @Column(name = "overall_score")
    @Builder.Default
    private Integer overallScore = null;

    @Column(name = "overall_feedback", columnDefinition = "TEXT")
    private String overallFeedback;

    @CreationTimestamp
    @Column(name = "timestamp_start", nullable = false, updatable = false)
    private ZonedDateTime timestampStart;

    @Column(name = "timestamp_end")
    @Builder.Default
    private ZonedDateTime timestampEnd = null;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Answer> answers;
}
