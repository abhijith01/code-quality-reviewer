package com.abhijith.code_quality_reviewer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "analysis_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private CodeFile codeFile;

    @Column(name = "total_issues", nullable = false)
    private Integer totalIssues;

    @Column(name = "severity_score", nullable = false)
    private Double severityScore;

    @Column(name = "ai_feedback", columnDefinition = "json")
    private String aiFeedback;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
