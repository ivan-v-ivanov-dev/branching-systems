package com.personal.users.repository;

import com.personal.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.personal.users.repository.query.Queries.FIND_USER_BY_ROLE_NAME_TEMPLATE;
import static com.personal.users.repository.query.Queries.FIND_USER_COUNT_BY_ROLE_NAME_TEMPLATE;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(FIND_USER_BY_ROLE_NAME_TEMPLATE)
    List<User> findAllUsersByRole(@Param("roleName") String roleName);


    @Query(FIND_USER_COUNT_BY_ROLE_NAME_TEMPLATE)
    int findAllUsersCountByRole(@Param("roleName") String roleName);


    User findByUsername(String username);
}
