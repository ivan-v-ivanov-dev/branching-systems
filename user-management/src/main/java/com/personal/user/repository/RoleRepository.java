package com.personal.user.repository;

import com.personal.user.model.Role;
import com.personal.user.repository.query.Queries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(Queries.FIND_ALL_ROLES)
    List<String> findAllRoles();

    @Modifying
    @Transactional
    @Query(Queries.RENAME_ROLE_TEMPLATE)
    int renameByName(@Param("oldName") String oldName, @Param("newName") String newName);

    Role findByName(String role);

    @Modifying
    @Transactional
    @Query(Queries.DELETE_ROLE_TEMPLATE)
    int deleteByName(@Param("name") String name);
}
