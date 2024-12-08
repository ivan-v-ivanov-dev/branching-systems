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

    TeamGatewayRp addProject(String teamName, String projectName);

    TeamGatewayRp removeProject(String teamName, String projectName);

    boolean delete(String name);

    List<TeamGatewayRp> search(String name, String projectName, int page, int size);

    List<TeamGatewayRp> findAllProjectTeams(String name, int page, int size);
}
