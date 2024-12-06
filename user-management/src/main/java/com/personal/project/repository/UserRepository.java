package com.personal.project.repository;

import com.personal.project.model.User;
import com.personal.project.repository.query.Queries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(Queries.FIND_USER_BY_ROLE_NAME_TEMPLATE)
    List<User> findAllUsersByRole(@Param("roleName") String roleName);


    @Query(Queries.FIND_USER_COUNT_BY_ROLE_NAME_TEMPLATE)
    int findAllUsersCountByRole(@Param("roleName") String roleName);


    User findByUsername(String username);

    Page<User> findByFirstNameContaining(String firstName, Pageable pageable);
}
