package com.personal.project.service.feign;

import com.personal.model.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserClient", url = "http://localhost:8082")
public interface UserClient {

    @GetMapping("/user/{id}")
    UserResponse findUserById(@PathVariable("id") int id);
}
