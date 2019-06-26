package com.example.kinoprokat.models;

import java.io.Serializable;

public class ReportWithoutCont implements Serializable {

    private String _id;
    private String movie;
    private String distributor;
    private String country;
    private int sessionCount;

    public String get_id() {
        return _id;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(int sessionCount) {
        this.sessionCount = sessionCount;
    }
}
