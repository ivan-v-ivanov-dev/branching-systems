package com.personal.gateway.service.feign;

import com.personal.model.dto.VacationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Vacation-Management", url = "http://localhost:8084")
public interface VacationManagementClient {

    @GetMapping("/user/{name}/vacations")
    List<VacationResponse> findUserVacations(@PathVariable("name") String name);
}
