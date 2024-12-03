package com.personal.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProjectController {

    @GetMapping("/health")
    public String health() {
        return "Healthy";
    }
}
