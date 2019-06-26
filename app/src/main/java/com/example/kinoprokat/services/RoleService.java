package com.example.kinoprokat.services;

import com.example.kinoprokat.models.Permission;
import com.example.kinoprokat.models.Role;

public class RoleService {

    private String role_key = "role";
    private static RoleService mInstance;
    private Role currentRole;

    public final String th_report_key = "report-theater";

    public static RoleService getInstance() {
        if (mInstance == null) {
            mInstance = new RoleService();
        }
        return mInstance;
    }

    public Role getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(Role currentRole) {
        this.currentRole = currentRole;
    }

    public boolean checkPermission(int value, String groupName) {
        return currentRole.getPermissions().contains(new Permission(value, groupName));
    }

    public String getRole_key() {
        return role_key;
    }
}
