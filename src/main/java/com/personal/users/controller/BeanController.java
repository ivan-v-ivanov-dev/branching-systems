package com.personal.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanController {

    @GetMapping
    public String health() {
        return "Healthy";
    }
}
