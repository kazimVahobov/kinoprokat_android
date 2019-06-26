package com.example.kinoprokat.models;

public class User {
    private String _id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private String roleId;
    private String imageSrc;
    private String theaterId;
    private String distId;
    private String movieId;
    private String newPassword;

    public String get_id() {
        return _id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public String getDistId() {
        return distId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
