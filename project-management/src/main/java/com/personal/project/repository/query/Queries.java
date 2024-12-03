package com.personal.project.repository.query;

public class Queries {

    public static final String DELETE_TEAM_TEMPLATE = "DELETE FROM Team t WHERE t.name = :name";

    public static final String FIND_BY_NAME_AND_PROJECT_NAME_TEMPLATE =
            "SELECT t FROM Team t JOIN t.project p WHERE t.name LIKE %:name% AND p.name LIKE %:projectName%";

    private Queries() {
    }
}
