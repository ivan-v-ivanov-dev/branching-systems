package com.personal.project.repository.query;

import com.personal.project.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamsRepository extends JpaRepository<Team, Integer> {
    Page<Team> findAll(Pageable pageable);

    Team findByName(String name);
}
