package com.example.kinoprokat.models;

import java.util.Date;

public class Contract {

    private String _id;
    private String movieId;
    private String firstSide;
    private String secondSide;
    private String contNum;
    private Date fromDate;
    private Date toDate;
    private int typeOfCont;
    private String movieUser;

    // theater prices
    private int dayChildPriceTh;
    private int dayAdultPriceTh;
    private int eveningChildPriceTh;
    private int eveningAdultPriceTh;
    // mobile theaters prices
    private int dayChildPriceMobile;
    private int dayAdultPriceMobile;
    private int eveningChildPriceMobile;
    private int eveningAdultPriceMobile;


    public String get_id() {
        return _id;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getFirstSide() {
        return firstSide;
    }

    public String getSecondSide() {
        return secondSide;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public int getTypeOfCont() {
        return typeOfCont;
    }

    public String getMovieUser() {
        return movieUser;
    }

    public String getContNum() {
        return contNum;
    }

    public int getDayChildPriceTh() {
        return dayChildPriceTh;
    }

    public int getDayAdultPriceTh() {
        return dayAdultPriceTh;
    }

    public int getEveningChildPriceTh() {
        return eveningChildPriceTh;
    }

    public int getEveningAdultPriceTh() {
        return eveningAdultPriceTh;
    }

    public int getDayChildPriceMobile() {
        return dayChildPriceMobile;
    }

    public int getDayAdultPriceMobile() {
        return dayAdultPriceMobile;
    }

    public int getEveningChildPriceMobile() {
        return eveningChildPriceMobile;
    }

    public int getEveningAdultPriceMobile() {
        return eveningAdultPriceMobile;
    }
}
