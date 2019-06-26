package com.example.kinoprokat.models;

import java.util.List;

public class Role {
    private String _id;
    private String name;
    private int typeOfRole;
    private List<Permission> permissions;

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public int getTypeOfRole() {
        return typeOfRole;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
