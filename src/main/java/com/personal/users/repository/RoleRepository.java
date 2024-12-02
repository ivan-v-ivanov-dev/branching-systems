package com.personal.users.repository;

import com.personal.users.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.personal.users.repository.query.Queries.FIND_ALL_ROLES;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(FIND_ALL_ROLES)
    List<String> findAllRoles();

    @Modifying
    @Transactional
    @Query("DELETE FROM Role r WHERE r.name = :name")
    int deleteByName(@Param("name") String name);

}
