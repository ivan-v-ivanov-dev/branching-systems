package com.personal.users.repository.query;

public class Queries {

    public static final String FIND_ALL_ROLES = "SELECT DISTINCT r.name FROM Role r ORDER BY r.name";

    public static final String RENAME_ROLE_TEMPLATE = "UPDATE Role r SET r.name = :newName WHERE r.name = :oldName";

    public static final String DELETE_ROLE_TEMPLATE = "DELETE FROM Role r WHERE r.name = :name";

    public static final String FIND_USER_BY_ROLE_NAME_TEMPLATE = "SELECT u FROM User u WHERE u.role.name = :roleName";

    private Queries() {
    }
}
