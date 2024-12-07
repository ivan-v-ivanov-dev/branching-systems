package com.personal.gateway.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "User-Management", url = "http://localhost:8082")
public interface UserManagementClient {

    @GetMapping("/roles")
    List<String> findAllRoles();

}


