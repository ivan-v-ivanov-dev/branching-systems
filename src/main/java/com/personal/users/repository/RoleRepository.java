package com.personal.users.repository;

import com.personal.users.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.personal.users.repository.query.Queries.*;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(FIND_ALL_ROLES)
    List<String> findAllRoles();

    @Modifying
    @Transactional
    @Query(RENAME_ROLE_TEMPLATE)
    int renameByName(@Param("oldName") String oldName, @Param("newName") String newName);

    @Modifying
    @Transactional
    @Query(DELETE_ROLE_TEMPLATE)
    int deleteByName(@Param("name") String name);

}
