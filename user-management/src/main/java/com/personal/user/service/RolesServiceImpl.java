package com.personal.user.service;

import com.personal.user.model.Role;
import com.personal.user.repository.RoleRepository;
import com.personal.user.repository.UserRepository;
import com.personal.user.service.contract.RolesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class RolesServiceImpl implements RolesService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<String> findAllRoles() {
        List<String> allRoles = roleRepository.findAllRoles();
        log.info("Retrieve all distinct roles");
        return allRoles;
    }

    @Override
    public Role create(String role) {
        Role createdRole = roleRepository.save(Role.builder().name(role).build());
        log.info(format("Create role: %s", createdRole.getName()));
        return createdRole;
    }

    @Override
    public boolean rename(String oldRole, String newRole) {
        int isRenamed = roleRepository.renameByName(oldRole, newRole);
        if (isRenamed <= 0) {
            log.warn(format("Can't rename %s", oldRole));
            return false;
        }
        log.info(format("Role renamed: %s", newRole));
        return true;
    }

    @Override
    public boolean delete(String role) {
        int isDeleted = roleRepository.deleteByName(role);
        if (isDeleted <= 0) {
            log.warn(format("Can't delete %s", role));
            return false;
        }
        log.info(format("Role deleted: %s", role));
        return true;
    }

    @Override
    public List<Role> findAllRolesUsersCount() {
        List<Role> roles = roleRepository.findAll();
        roles.forEach(role -> role.setUserCount(userRepository.findAllUsersCountByRole(role.getName())));
        log.info("Retrieve all roles' user count");
        return roles;
    }
}
