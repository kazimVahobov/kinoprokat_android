package com.example.kinoprokat.models;

public class Movie {
    private String _id;
    private String name;
    private String contId;
    private int minPriceTicket;

    public int getMinPriceTicket() {
        return minPriceTicket;
    }

    public void setMinPriceTicket(int minPriceTicket) {
        this.minPriceTicket = minPriceTicket;
    }

    public Movie(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public Movie() {
    }

    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
