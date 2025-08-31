package com.abhijith.code_quality_reviewer.service;

import com.abhijith.code_quality_reviewer.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepo projectRepo;

    public ResponseEntity<?> createProject(long id){return null;}

}
