package com.personal.gateway.service.feign;

import com.personal.model.dto.VacationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Vacation-Management", url = "http://localhost:8084")
public interface VacationManagementClient {

    @GetMapping("/user/{name}/vacations")
    List<VacationResponse> findUserVacations(@PathVariable("name") String name);

    @PutMapping("/vacation/{id}")
    VacationResponse updateVacation(@PathVariable("id") String id,
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                    @RequestParam(value = "endDate", required = false) String endDate,
                                    @RequestParam(value = "halfDay") boolean halfDay);

    @DeleteMapping("/vacation/{id}")
    boolean deleteVacation(@PathVariable("id") String id);
}
