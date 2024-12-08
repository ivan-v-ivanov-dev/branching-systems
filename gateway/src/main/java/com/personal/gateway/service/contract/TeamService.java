package com.personal.gateway.service.contract;

import com.personal.model.dto.TeamGatewayRp;
import com.personal.model.dto.TeamGatewayRq;
import com.personal.model.dto.TeamResponse;

import java.util.List;

public interface TeamService {
    List<TeamGatewayRp> findAll(int page, int size, String sortBy);

    TeamGatewayRp findByName(String name);

    TeamGatewayRp create(TeamGatewayRq teamGatewayRq);

    TeamGatewayRp addMemberToATeam(String name, int id);

    TeamGatewayRp removeMemberFromATeam(String name, int id);
}
