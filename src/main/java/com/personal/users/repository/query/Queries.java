package com.personal.users.repository.query;

public class Queries {

    public static final String FIND_ALL_ROLES = "SELECT DISTINCT r.name FROM Role r ORDER BY r.name";

    private Queries() {
    }
}
