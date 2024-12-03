package com.personal.project.repository.query;

public class Queries {

    public static final String DELETE_TEAM_TEMPLATE = "DELETE FROM Team t WHERE t.name = :name";

    private Queries() {
    }
}
