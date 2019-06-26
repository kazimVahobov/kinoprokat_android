package com.example.kinoprokat.models;

import java.io.Serializable;

public class ReportWithCont implements Serializable {

    private String _id;
    private String movieId;
    private String contractId;
    private String holeId;
    private String sessionTime;
    private boolean daySession;
    private int childTicketCount;
    private int adultTicketCount;
    private int childTicketPrice;
    private int adultTicketPrice;

    public String get_id() {
        return _id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getHoleId() {
        return holeId;
    }

    public void setHoleId(String holeId) {
        this.holeId = holeId;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public boolean isDaySession() {
        return daySession;
    }

    public void setDaySession(boolean daySession) {
        this.daySession = daySession;
    }

    public int getChildTicketCount() {
        return childTicketCount;
    }

    public void setChildTicketCount(int childTicketCount) {
        this.childTicketCount = childTicketCount;
    }

    public int getAdultTicketCount() {
        return adultTicketCount;
    }

    public void setAdultTicketCount(int adultTicketCount) {
        this.adultTicketCount = adultTicketCount;
    }

    public int getChildTicketPrice() {
        return childTicketPrice;
    }

    public void setChildTicketPrice(int childTicketPrice) {
        this.childTicketPrice = childTicketPrice;
    }

    public int getAdultTicketPrice() {
        return adultTicketPrice;
    }

    public void setAdultTicketPrice(int adultTicketPrice) {
        this.adultTicketPrice = adultTicketPrice;
    }
}
