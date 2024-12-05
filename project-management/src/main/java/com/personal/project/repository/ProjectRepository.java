package com.personal.project.repository;

import com.personal.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByName(String name);

    int deleteByName(String name);
}
