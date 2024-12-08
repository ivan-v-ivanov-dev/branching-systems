package com.personal.gateway.service.contract;

import com.personal.model.dto.ProjectGatewayRp;

import java.util.List;

public interface ProjectService {
    List<ProjectGatewayRp> findAll(int page, int size);

    ProjectGatewayRp findByName(String name);
}
