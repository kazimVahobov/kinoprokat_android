package com.example.kinoprokat.models;

public class Login {
    private String userName;
    private String password;

    public Login (String _userName, String _password) {
        this.userName = _userName;
        this.password = _password;
    }

    public String getUserName() {
        return userName;
    }
}
