package com.example.kinoprokat.models;

public class Permission {
    private int value;

    private String groupName;

    public Permission() {
    }

    public Permission(int value, String groupName) {
        this.value = value;
        this.groupName = groupName;
    }

    public int getValue() {
        return value;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Permission) {
            Permission per = (Permission) obj;
            if (this.getGroupName().equals(per.groupName) && this.getValue() == per.getValue()) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }
}
