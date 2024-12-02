package com.personal.users.repository;

import com.personal.users.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static com.personal.users.repository.query.Queries.FIND_ALL_ROLES;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(FIND_ALL_ROLES)
    List<String> findAllRoles();
}
