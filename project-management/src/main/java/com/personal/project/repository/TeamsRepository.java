package com.personal.project.repository;

import com.personal.project.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import static com.personal.project.repository.query.Queries.DELETE_TEAM_TEMPLATE;
import static com.personal.project.repository.query.Queries.FIND_BY_NAME_AND_PROJECT_NAME_TEMPLATE;

public interface TeamsRepository extends JpaRepository<Team, Integer> {
    Page<Team> findAll(Pageable pageable);

    Team findByName(String name);

    @Modifying
    @Transactional
    @Query(DELETE_TEAM_TEMPLATE)
    int deleteByName(@Param("name") String name);

    @Query(FIND_BY_NAME_AND_PROJECT_NAME_TEMPLATE)
    Page<Team> searchByNameAndProjectName(@Param("name")String name,
                                          @Param("projectName") String projectName,
                                          PageRequest pageable);
}
