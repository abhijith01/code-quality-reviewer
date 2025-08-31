package com.abhijith.code_quality_reviewer.repo;

import com.abhijith.code_quality_reviewer.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
    Optional<Project> findByUserId(Long userId);
    Optional<Project> findByProjectName(String projectName);
}
