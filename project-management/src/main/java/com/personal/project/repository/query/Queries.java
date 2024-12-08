package com.personal.project.repository.query;

public class Queries {

    public static final String DELETE_TEAM_TEMPLATE = "DELETE FROM Team t WHERE t.name = :name";

//    public static final String FIND_BY_NAME_AND_PROJECT_NAME_TEMPLATE =
//            "SELECT t FROM Team t JOIN t.projects p WHERE t.name LIKE %:name% AND p.name LIKE %:projectName%";

    public static final String FIND_BY_NAME_AND_PROJECT_NAME_TEMPLATE =
            """
                    SELECT * FROM teams t 
                    JOIN team_projects pt ON t.id = pt.team_id 
                    JOIN projects p ON pt.project_id = p.id 
                    WHERE t.name LIKE  :name
                    AND p.name LIKE :projectName 
                    LIMIT :limit OFFSET :offset""";

    public static final String FIND_BY_PROJECT_NAME_TEMPLATE =
            """
                    SELECT * 
                    FROM teams t
                    JOIN team_projects tp ON t.id = tp.team_id
                    JOIN projects p ON tp.project_id = p.id
                    WHERE p.name LIKE :projectName
                    ORDER BY t.name ASC
                    LIMIT :limit OFFSET :offset""";

    private Queries() {
    }
}
