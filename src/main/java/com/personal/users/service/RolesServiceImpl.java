package com.personal.users.service;

import com.personal.users.repository.RoleRepository;
import com.personal.users.service.contract.RolesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RoleRepository roleRepository;

    @Override
    public List<String> findAllRoles() {
        return roleRepository.findAllRoles();
    }
}
